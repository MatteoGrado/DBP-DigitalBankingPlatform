package de.grado.immoservice.repository;

import de.grado.immoservice.model.PropertyImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImagesRepository extends JpaRepository<PropertyImages, Long>
{
}
