package com.toniclecb.springbootregistration.interfaces;

import java.util.Date;

/**
 * Restructuring the code to remove the direct dependency (Date) will allow to inject different implementations (runtime and test runtime)
 */
public interface DateTime {
    Date getDate();
}

