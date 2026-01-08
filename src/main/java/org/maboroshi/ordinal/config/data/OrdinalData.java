package org.maboroshi.ordinal.config.data;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;

@Configuration
public class OrdinalData {
    @Comment("The next available ordinal rank. DO NOT EDIT unless you know what you are doing.")
    public int nextOrdinal = 1;
}
