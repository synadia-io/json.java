// Copyright 2020-2025 The NATS Authors
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.synadia.json;

import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utilities for encoding, i.e., Base64, URI and JSON
 */
public abstract class Encoding {

    private Encoding() {}  /* ensures cannot be constructed */

    /**
     * base64 encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    public static byte @NotNull [] base64BasicEncode(byte @NotNull [] input) {
        return Base64.encodeBase64(input);
    }

    /**
     * base64 encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    @NotNull
    public static String base64BasicEncodeToString(byte @NotNull [] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    @NotNull
    public static String base64BasicEncodeToString(@NotNull String input) {
        return Base64.encodeBase64String(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @param charset the charset of the input string
     * @return the encoded byte array
     */
    @NotNull
    public static String base64BasicEncodeToString(@NotNull String input, @NotNull Charset charset) {
        return Base64.encodeBase64String(input.getBytes(charset));
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    public static byte @NotNull [] base64UrlEncode(byte @NotNull [] input) {
        return Base64.encodeBase64URLSafe(input);
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    @NotNull
    public static String base64UrlEncodeToString(byte @NotNull [] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @return the encoded byte array
     */
    @NotNull
    public static String base64UrlEncodeToString(@NotNull String input) {
        return Base64.encodeBase64URLSafeString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64 url encode a byte array to a byte array
     * @param input the input byte array to encode
     * @param charset the charset of the input string
     * @return the encoded byte array
     */
    @NotNull
    public static String base64UrlEncodeToString(@NotNull String input, @NotNull Charset charset) {
        return Base64.encodeBase64URLSafeString(input.getBytes(charset));
    }

    /**
     * base64 decode a byte array
     * @param input the input byte array to decode
     * @return the decoded byte array
     */
    public static byte @NotNull [] base64BasicDecode(byte@NotNull [] input) {
        return Base64.decodeBase64(input);
    }

    /**
     * base64 decode a base64 encoded string
     * @param input the input string to decode
     * @return the decoded byte array
     */
    public static byte @NotNull [] base64BasicDecode(@NotNull String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * base64 decode a base64 encoded string
     * @param input the input string to decode
     * @return the decoded string
     */
    @NotNull
    public static String base64BasicDecodeToString(@NotNull String input) {
        return new String(Base64.decodeBase64(input), StandardCharsets.UTF_8);
    }

    /**
     * base64 decode a base64 encoded string
     * @param input the input string to decode
     * @param charset the charset to use when decoding the decoded bytes to string
     * @return the decoded string
     */
    @NotNull
    public static String base64BasicDecodeToString(@NotNull String input, @NotNull Charset charset) {
        return new String(Base64.decodeBase64(input), charset);
    }

    /**
     * base64 url decode a byte array
     * @param input the input byte array to decode
     * @return the decoded byte array
     */
    public static byte @NotNull [] base64UrlDecode(byte@NotNull [] input) {
        return Base64.decodeBase64(input);
    }

    /**
     * base64 url decode a byte array
     * @param input the input byte array to decode
     * @return the decoded byte array
     */
    @NotNull
    public static String base64UrlDecodeToString(byte@NotNull [] input) {
        return new String(Base64.decodeBase64(input));
    }

    /**
     * base64 url decode a base64 url encoded string
     * @param input the input string to decode
     * @return the decoded byte array
     */
    public static byte @NotNull [] base64UrlDecode(@NotNull String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * base64 url decode a base64 url encoded string
     * @param input the input string to decode
     * @return the decoded string
     */
    @NotNull
    public static String base64UrlDecodeToString(@NotNull String input) {
        return new String(Base64.decodeBase64(input));
    }

    /**
     * Decode a JSON string
     * @param s the input string
     * @return the decoded string
     */
    @NotNull
    public static String jsonDecode(@NotNull String s) {
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int x = 0; x < len; x++) {
            char ch = s.charAt(x);
            if (ch == '\\') {
                char nextChar = (x == len - 1) ? '\\' : s.charAt(x + 1);
                switch (nextChar) {
                    case '\\':
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (x >= len - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                            "" + s.charAt(x + 2) + s.charAt(x + 3) + s.charAt(x + 4) + s.charAt(x + 5), 16);
                        sb.append(Character.toChars(code));
                        x += 5;
                        continue;
                    default:
                        ch = nextChar;
                        break;
                }
                x++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * Encode a JSON string
     * @param s the input string
     * @return the encoded string
     */
    @NotNull
    public static String jsonEncode(@Nullable String s) {
        return s == null ? "" : jsonEncode(new StringBuilder(), s).toString();
    }

    /**
     * Encode a JSON string into a StringBuilder
     * @param sb the target StringBuilder
     * @param s the input string
     * @return the encoded string as StringBuilder
     */
    @NotNull
    public static StringBuilder jsonEncode(@NotNull StringBuilder sb, @Nullable String s) {
        int len = s == null ? 0 : s.length();
        for (int x = 0; x < len; x++) {
            char ch = s.charAt(x);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch < ' ') {
                        sb.append(String.format("\\u%04x", (int) ch));
                    }
                    else {
                        sb.append(ch);
                    }
                    break;
            }
        }
        return sb;
    }

    /**
     * Decode a URI
     * @param source the input
     * @return the decoded URI
     */
    public static String uriDecode(@NotNull String source) {
        return URLDecoder.decode(source.replace("+", "%2B"), StandardCharsets.UTF_8);
    }
}
