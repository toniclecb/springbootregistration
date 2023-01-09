package com.toniclecb.springbootregistration.domain.entities;

import java.io.Serializable;
import java.util.UUID;

public interface AbstractEntity extends Serializable {
    public UUID getId();
}
