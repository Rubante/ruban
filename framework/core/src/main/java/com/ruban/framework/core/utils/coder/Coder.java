/**
 * File name：Coder.java
 * Creation date ：2012-11-01
 * Copyright (c) 2012 tendyron
 * All rights reserved.
 */

package com.ruban.framework.core.utils.coder;

public interface Coder {

    public String encode(byte[] data);

    public byte[] decode(String string);

}