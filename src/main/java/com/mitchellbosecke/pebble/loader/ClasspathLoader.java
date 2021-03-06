/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Copyright (c) 2014 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mitchellbosecke.pebble.error.LoaderException;

/**
 * Uses a classloader to find templates located on the classpath.
 * 
 * @author mbosecke
 *
 */
public class ClasspathLoader implements Loader {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathLoader.class);

    private String prefix;

    private String suffix;

    private String charset = "UTF-8";

    @Override
    public Reader getReader(String templateName) throws LoaderException {

        InputStreamReader isr = null;
        Reader reader = null;

        InputStream is = null;

        // append the prefix and make sure prefix ends with a separator
        // character
        StringBuilder path = new StringBuilder("");
        if (getPrefix() != null) {

            path.append(getPrefix());

            if (!getPrefix().endsWith(String.valueOf(File.separatorChar))) {
                path.append(File.separatorChar);
            }
        }

        String location = path.toString() + templateName + (getSuffix() == null ? "" : getSuffix());
        logger.debug("Looking for template in {}.", location);

        // perform the lookup
        ClassLoader rcl = ClasspathLoader.class.getClassLoader();
        is = rcl.getResourceAsStream(location);

        if (is == null) {
            throw new LoaderException(null, "Could not find template \"" + location + "\"");
        }

        try {
            isr = new InputStreamReader(is, charset);
            reader = new BufferedReader(isr);
        } catch (UnsupportedEncodingException e) {
        }

        return reader;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
