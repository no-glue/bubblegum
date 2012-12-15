/*
 * MIT license
 *
 * Copyright (c) 2012 Janick Reynders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.janickreynders.bubblegum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Request {
    private final HttpServletRequest req;
    private final Map<String, String> params;

    public Request(HttpServletRequest req, Map<String, String> params) {
        this.req = req;
        this.params = params;
    }

    public HttpServletRequest raw() {
        return req;
    }

    public String param(String name) {
        return params.get(name);
    }

    public void attribute(String name, Object val) {
        req.setAttribute(name, val);
    }

    public void forward(String url, Response response) {
        try {
            raw().getRequestDispatcher(url).forward(raw(), response.raw());
        } catch (ServletException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public Set<String> queryParams() {
        return new HashSet<String>(req.getParameterMap().keySet());
    }

    public String queryParam(String name) {
        return req.getParameter(name);
    }
}