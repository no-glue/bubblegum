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

public class Matchers {

    public static RequestMatcher method(final String httpMethod) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                return Match.when(httpMethod.equalsIgnoreCase(req.method()));
            }
        };
    }

    public static RequestMatcher header(final String headerName, final CharSequence value) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                return Match.when(req.header(headerName).contains(value));
            }
        };
    }

    public static RequestMatcher accept(final CharSequence mimeType) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                String accept = req.header("Accept");
                return Match.when(accept.contains(mimeType) || accept.equals("*/*"));
            }
        };
    }

    public static RequestMatcher contentType(CharSequence contentType) {
        return header("Content-Type", contentType);
    }

    public static RequestMatcher matchRoute(String route) {
        return new Route(route);
    }

    public static RequestMatcher all(final RequestMatcher... matchers) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                Match result = Match.match();
                for (RequestMatcher matcher : matchers) {
                    result.and(matcher.matches(req));
                }
                return result;
            }
        };
    }

    public static RequestMatcher any(final RequestMatcher... matchers) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                Match result = Match.noMatch();
                for (RequestMatcher matcher : matchers) {
                    result.or(matcher.matches(req));
                }
                return result;
            }
        };
    }

    public static RequestMatcher not(final RequestMatcher matcher) {
        return new RequestMatcher() {
            @Override
            public Match matches(Request req) {
                return matcher.matches(req).negate();
            }
        };
    }
}
