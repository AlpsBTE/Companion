package com.alpsbte.companion.core.config;

public abstract class ConfigPaths {

    // HUB CONNECTION
    private static final String HUB = "hub.";
    public static final String HUB_IP = HUB + "IP";
    public static final String HUB_PORT = HUB + "port";


    // SPAWN POINTS
    private static final String SPAWN_POINTS = "spawn-points.";

    private static final String SPAWN_POINTS_MAP = SPAWN_POINTS + ".map";
    public static final String SPAWN_POINTS_MAP_X = SPAWN_POINTS_MAP + ".x";
    public static final String SPAWN_POINTS_MAP_Y = SPAWN_POINTS_MAP + ".y";
    public static final String SPAWN_POINTS_MAP_Z = SPAWN_POINTS_MAP + ".z";
    public static final String SPAWN_POINTS_MAP_YAW = SPAWN_POINTS_MAP + ".yaw";
    public static final String SPAWN_POINTS_MAP_PITCH = SPAWN_POINTS_MAP + ".pitch";

    private static final String SPAWN_POINTS_TREES = SPAWN_POINTS + ".trees";
    public static final String SPAWN_POINTS_TREES_X = SPAWN_POINTS_TREES + ".x";
    public static final String SPAWN_POINTS_TREES_Y = SPAWN_POINTS_TREES + ".y";
    public static final String SPAWN_POINTS_TREES_Z = SPAWN_POINTS_TREES + ".z";
    public static final String SPAWN_POINTS_TREES_YAW = SPAWN_POINTS_TREES + ".yaw";
    public static final String SPAWN_POINTS_TREES_PITCH = SPAWN_POINTS_TREES + ".pitch";


    // FORMATTING
    public static final String MESSAGE_PREFIX = "message-prefix";
    public static final String MESSAGE_INFO_COLOUR = "info-colour";
    public static final String MESSAGE_ERROR_COLOUR = "error-colour";


    // CONFIG VERSION
    public static final String CONFIG_VERSION = "config-version";
}
