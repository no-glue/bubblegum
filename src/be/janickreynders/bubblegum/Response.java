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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Response {
    private HttpServletResponse resp;

    public Response(HttpServletResponse resp) {
        this.resp = resp;
    }

    public HttpServletResponse raw() {
        return resp;
    }

    public void ok(CharSequence c) {
        try {
            resp.getWriter().append(c);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public void error(int code) {
        try {
            resp.sendError(code);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public void type(String contentType) {
        resp.setContentType(contentType);
    }

    public void redirect(String url) {
        try {
            resp.sendRedirect(url);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}