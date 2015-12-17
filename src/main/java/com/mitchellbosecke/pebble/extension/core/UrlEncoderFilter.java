/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Copyright (c) 2014 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.extension.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.escaper.RawString;

public class UrlEncoderFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }
        String arg = input instanceof RawString ? ((RawString) input).rawString() : (String) input;
        try {
            arg = URLEncoder.encode(arg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return arg;
    }

}
