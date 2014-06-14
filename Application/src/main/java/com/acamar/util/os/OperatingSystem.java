package com.acamar.util.os;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public enum OperatingSystem
{
    WINDOWS_OS("Windows", OperatingSystemFamily.WINDOWS),
    LINUX_OS("Linux", OperatingSystemFamily.LINUX),
    MAC_OS("Mac OS", OperatingSystemFamily.MAC),
    UNKNOWN_OS("Unknown OS", OperatingSystemFamily.OTHER);

    private String label;
    private OperatingSystemFamily family;

    OperatingSystem(String label, OperatingSystemFamily family)
    {
        this.label = label;
        this.family = family;
    }

    public OperatingSystemFamily getFamily()
    {
        return family;
    }

    public String getLabel()
    {
        return label;
    }

    public static OperatingSystem resolve()
    {
        String osName = System.getProperty("os.name").toLowerCase();

        for (OperatingSystem os : OperatingSystem.values()) {
            if (osName.contains(os.getLabel().toLowerCase())) {
                return os;
            }
        }

        return OperatingSystem.UNKNOWN_OS;
    }
}
