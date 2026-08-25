package de.grado.immoservice.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyEvent
{
    private String imagesUrl;

    public PropertyEvent(String url)
    {
    }
}
