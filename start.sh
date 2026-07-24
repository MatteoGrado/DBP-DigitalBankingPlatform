#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_FILE="$ROOT_DIR/compose.yml"
ACTION="${1:-start}"
RUNTIME_DIR="$ROOT_DIR/.runtime"
PID_DIR="$RUNTIME_DIR/pids"
LOG_DIR="$RUNTIME_DIR/logs"

INFRA_SERVICES=(
  mysql
  rabbitmq
  kafka
  elasticsearch
  mailhog
  redis
  consul
  adminer
)

MICROSERVICES=(
  "Customer-Service"
)

GREEN='\033[0;32m'
RESET='\033[0m'

log() {
  printf '%b[%s] %s%b\n' "$GREEN" "$(date '+%Y-%m-%d %H:%M:%S')" "$*" "$RESET"
}

docker_ready() {
  docker info >/dev/null 2>&1
}

start_docker() {
  log "Docker laeuft nicht. Starte Docker Desktop..."

  if command -v open >/dev/null 2>&1; then
    open -a Docker >/dev/null 2>&1 || true
  elif command -v systemctl >/dev/null 2>&1; then
    sudo systemctl start docker
  elif command -v service >/dev/null 2>&1; then
    sudo service docker start
  else
    log "Docker konnte nicht automatisch gestartet werden."
    exit 1
  fi

  for _ in $(seq 1 120); do
    if docker_ready; then
      return
    fi
    sleep 1
  done

  log "Docker wurde nicht rechtzeitig startklar."
  exit 1
}

usage() {
  log "Usage: $0 {start|stop}"
}

ensure_runtime_dirs() {
  mkdir -p "$PID_DIR" "$LOG_DIR"
}

start_infrastructure() {
  log "Starte Infrastruktur via docker compose..."
  docker compose -f "$COMPOSE_FILE" up -d "${INFRA_SERVICES[@]}"
}

start_microservice() {
  local service_name="$1"
  local service_dir="$ROOT_DIR/$service_name"
  local pid_file="$PID_DIR/$service_name.pid"
  local log_file="$LOG_DIR/$service_name.log"

  if [[ ! -d "$service_dir" ]]; then
    log "Verzeichnis fehlt: $service_dir"
    exit 1
  fi

  if [[ -f "$pid_file" ]] && kill -0 "$(cat "$pid_file")" >/dev/null 2>&1; then
    log "$service_name laeuft bereits (PID $(cat "$pid_file"))."
    return
  fi

  log "Starte $service_name via mvn spring-boot:run..."
  (
    cd "$service_dir"
    nohup ./mvnw spring-boot:run >"$log_file" 2>&1 &
    echo $! >"$pid_file"
  )
}

start_microservices() {
  ensure_runtime_dirs

  for service_name in "${MICROSERVICES[@]}"; do
    start_microservice "$service_name"
  done

  log "Alle Microservices wurden gestartet."
  log "Logs liegen unter $LOG_DIR."
}

stop_microservices() {
  local pid_file pid service_name

  if [[ ! -d "$PID_DIR" ]]; then
    log "Keine laufenden Microservice-PIDs gefunden."
    return
  fi

  for pid_file in "$PID_DIR"/*.pid; do
    [[ -e "$pid_file" ]] || continue
    service_name="$(basename "$pid_file" .pid)"
    pid="$(cat "$pid_file")"

    if kill -0 "$pid" >/dev/null 2>&1; then
      log "Stoppe $service_name (PID $pid)..."
      kill "$pid"
    else
      log "$service_name ist nicht mehr aktiv."
    fi

    rm -f "$pid_file"
  done
}

case "$ACTION" in
  start|stop)
    ;;
  *)
    usage
    exit 1
    ;;
esac

if [[ "$ACTION" == "start" ]]; then
  if docker_ready; then
    log "Docker laeuft bereits."
  else
    start_docker
  fi

  start_infrastructure
  start_microservices
else
  log "Stoppe lokale Microservices..."
  stop_microservices

  if docker_ready; then
    log "Stoppe Infrastruktur via docker compose..."
    docker compose -f "$COMPOSE_FILE" stop
  else
    log "Docker laeuft nicht, Infrastruktur wurde nicht gestoppt."
  fi

  log "Alle Services gestoppt."
fi
