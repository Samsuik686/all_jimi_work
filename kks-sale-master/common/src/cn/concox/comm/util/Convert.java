package cn.concox.comm.util;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Convert {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final long ONEDAY = 86399999L;
	public static String serial = "零一二三四五六七八九十";

	public static int getint(String s) {
		int i = 0;
		if (s == null)
			return 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	public static byte getbyte(String s) {
		byte b = 0;
		if (s == null)
			return 0;
		try {
			b = Byte.parseByte(s);
		} catch (Exception e) {
			return 0;
		}
		return b;
	}

	public static short getshort(String s) {
		short i = 0;
		if (s == null)
			return 0;
		try {
			i = Short.parseShort(s);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	public static long getlong(String s) {
		long l = 0L;
		if (s == null)
			return 0L;
		try {
			l = Long.parseLong(s);
		} catch (Exception e) {
			return 0L;
		}
		return l;
	}

	public static long[] getlongArray(String s, String d) {
		if ((s == null) || (s.length() == 0))
			return null;
		return getlongArray(split(s, d));
	}

	public static long[] getlongArray(String[] sa) {
		if ((sa == null) || (sa.length == 0))
			return null;
		long[] r = new long[sa.length];
		for (int i = 0; i < sa.length; i++) {
			r[i] = getlong(sa[i]);
		}
		return r;
	}

	public static float getfloat(String s) {
		float f = 0.0F;
		if (s == null)
			return 0.0F;
		try {
			f = Float.parseFloat(s);
		} catch (Exception e) {
			return 0.0F;
		}
		return f;
	}

	public static double getdouble(String s) {
		double d = 0.0D;
		if (s == null)
			return 0.0D;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			return 0.0D;
		}
		return d;
	}

	public static Date getDate(String s) {
		if (s == null) {
			return null;
		}
		Date d = null;
		try {
			if (s.length() > 10)
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
			else
				d = new SimpleDateFormat("yyyy-MM-dd").parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static Date getDate(String s, String format) {
		if (s == null) {
			return null;
		}
		Date d = null;
		try {
			d = new SimpleDateFormat(format).parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static Date getDateOneDay(String s, boolean endMode) {
		if ((s == null) || (s.length() == 0)) {
			return null;
		}
		Date d = null;
		try {
			if (s.length() > 10)
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
			else {
				d = new SimpleDateFormat("yyyy-MM-dd").parse(s);
			}
			if ((endMode) && (s.length() <= 10))
				d = new Date(d.getTime() + 86399999L);
		} catch (Exception localException) {
		}
		return d;
	}

	public static Date getDateDayEnd(Date begin) {
		return new Date(begin.getTime() + 86399999L);
	}

	public static boolean getboolean(String s) {
		if (s == null)
			return false;
		if (s.trim().toLowerCase().equals("true")) {
			return true;
		}
		return s.trim().equals("1");
	}

	public static String getString(String s) {
		return s;
	}

	public static String getStringKey(String s) {
		return s;
	}

	public static BigDecimal getBigDecimal(String s) {
		return new BigDecimal(s);
	}

	public static char getchar(String s) {
		if (s.length() == 0)
			return ' ';
		char[] chs = s.toCharArray();
		return chs[0];
	}

	public static Date getDate(String s, int off) {
		Date d = getDate(s);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(5, off);
		return c.getTime();
	}

	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	public static Boolean getBoolean(String s) {
		return new Boolean(getboolean(s));
	}

	public static Integer getInteger(String s) {
		return new Integer(getint(s));
	}

	public static Byte getByte(String s) {
		return new Byte(s);
	}

	public static Short getShort(String s) {
		return new Short(getshort(s));
	}

	public static Long getLong(String s) {
		return new Long(getlong(s));
	}

	public static Float getFloat(String s) {
		return new Float(getfloat(s));
	}

	public static Double getDouble(String s) {
		return new Double(getdouble(s));
	}

	public static String replace(String str, String r, String t) {
		if (r == null)
			return str;
		if (str == null) {
			return str;
		}
		int p = str.indexOf(r);
		int last = 0;
		if (p == -1)
			return str;
		StringBuffer strb = new StringBuffer(str.length() << 1);

		while (p >= 0) {
			strb.append(str.substring(last, p));
			if (t != null)
				strb.append(t);
			last = p + r.length();
			p = str.indexOf(r, last);
		}
		strb.append(str.substring(last));
		return strb.toString();
	}

	public static String encodeRequestValue(String str, String charset) {
		String d = encode(str, charset);
		d = replace(d, encode("&", charset), "&");
		d = replace(d, encode("=", charset), "=");
		return d;
	}

	public static String removeInvalidChar(String s, String invalid) {
		if (s == null)
			return null;
		StringBuffer strb = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (invalid.indexOf(c) == -1)
				strb.append(c);
		}
		return strb.toString();
	}

	public static String[] split(String str, String s) {
		if (str == null) {
			return null;
		}
		if (s == null) {
			return new String[] { str };
		}
		StringTokenizer st = new StringTokenizer(str, s);
		String[] r = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			r[(i++)] = st.nextToken();
		}
		return r;
	}

	public static int[] splitToint(String str, String s) {
		String[] strs = split(str, s);
		if (strs == null)
			return null;
		int[] ints = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			ints[i] = getint(strs[i]);
		}
		return ints;
	}

	public static int[] getIntArray(String[] s) {
		if (s == null)
			return null;
		int[] r = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			r[i] = getint(s[i]);
		}
		return r;
	}

	public static String[] getStringArray(int[] s) {
		if (s == null)
			return null;
		String[] r = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			r[i] = String.valueOf(s[i]);
		}
		return r;
	}

	public static String removeTag(String html) {
		if (html == null)
			return null;
		StringBuffer strb = new StringBuffer(html.length());
		int begin = 0;
		int p = html.indexOf("<");
		while ((p != -1) && (p < html.length() - 1)) {
			if (html.charAt(p + 1) < '') {
				int p1 = html.indexOf(">", p + 1);
				int p2 = html.indexOf("<", p + 1);
				if ((p1 != -1) && ((p1 < p2) || (p2 == -1))) {
					strb.append(html.substring(begin, p));
					begin = p1 + 1;
				}
				p = p2;
			} else {
				p = html.indexOf("<", p + 1);
			}
		}
		strb.append(html.substring(begin));
		String str = strb.toString();
		str = replace(str, "&nbsp;", " ");
		str = replace(str, "&amp;", "&");
		str = replace(str, "&lt;", "<");
		str = replace(str, "&gt;", ">");
		str = replace(str, "&brvbar;", "¦");
		str = replace(str, "&quot;", "\"");
		str = replace(str, "&middot;", "·");
		str = replace(str, "&bull;", "·");
		return trim(str);
	}

	public static String trim(String str) {
		if (str == null)
			return null;
		int i = 0;
		for (i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((c != ' ') && (c != '\r') && (c != '\n') && (c != '\t'))
				break;
		}
		if (i == str.length())
			return "";
		int e = 0;
		for (e = str.length() - 1; e >= 0; e--) {
			char c = str.charAt(e);
			if ((c != ' ') && (c != '\r') && (c != '\n') && (c != '\t'))
				break;
		}
		return str.substring(i, e + 1);
	}

	public static String getString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar dc = Calendar.getInstance();
		dc.setTime(date);
		Calendar now = Calendar.getInstance();

		int nd = now.get(6);
		int dd = dc.get(6);
		int ny = now.get(1);
		int dy = dc.get(1);
		if ((dy > 2000) && (dy < 2100)) {
			if (dy == ny) {
				if (dc.get(2) == now.get(2))
					switch (nd - dd) {
					case 0:
						sdf.applyPattern("HH:mm");
						break;
					case 1:
						sdf.applyPattern("昨天 HH:mm");
						break;
					case 2:
						sdf.applyPattern("前天 HH:mm");
						break;
					default:
						sdf.applyPattern("d日 HH:mm");
						break;
					}
				else
					sdf.applyPattern("MM月dd日 HH:mm");
			} else
				sdf.applyPattern("yy年MM月dd日 HH:mm");
		} else {
			sdf.applyPattern("yyyy年MM月dd日 HH:mm");
		}
		return sdf.format(date);
	}

	public static String getDateString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("M月d日");
		return sdf.format(date);
	}

	public static String getDateString(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (format == null)
			sdf.applyPattern("yyyy-MM-dd");
		else
			sdf.applyPattern(format);
		return sdf.format(date);
	}

	public String getTimeString() {
		StringBuffer sb = new StringBuffer();
		Calendar c = Calendar.getInstance();
		sb.append(String.valueOf(c.get(1))).append("_").append(String.valueOf(c.get(2) + 1)).append("_").append(String.valueOf(c.get(5))).append("_").append(String.valueOf(c.get(11))).append("_").append(String.valueOf(c.get(12))).append("_").append(String.valueOf(c.get(13))).append("_").append(String.valueOf(c.get(14)));
		return sb.toString();
	}

	public static String encodeDef(String str) {
		return encode(str, "utf-8");
	}

	public static String encode(String str, String enc) {
		if ((str == null) || (str.trim().length() == 0))
			return "";
		try {
			return URLEncoder.encode(str, enc);
		} catch (Exception e) {
		}
		return "";
	}

	public static String decode(String rawKey, String enc) {
		if ((rawKey == null) || (rawKey.trim().length() == 0))
			return "";
		String key = null;
		try {
			key = new String(rawKey.getBytes("8859_1"), enc);
		} catch (UnsupportedEncodingException e) {
			return rawKey;
		}
		return key;
	}

	public static String decoder(String str, String enc) {
		if ((str == null) || (str.trim().length() == 0))
			return "";
		try {
			return URLDecoder.decode(str, enc);
		} catch (Exception e) {
		}
		return "";
	}

	public static String nullToEmpty(String str) {
		return str == null ? "" : str.trim();
	}

	public static String toEntities(String str, String rp) {
		StringBuffer msg = new StringBuffer(str.length() * 2);
		for (char c : str.toCharArray()) {
			switch (c) {
			case '<':
				msg.append("&#60;");
				break;
			case '>':
				msg.append("&#62;");
				break;
			case '&':
				msg.append("&#38;");
				break;
			case '"':
				msg.append("&#34;");
				break;
			case '\'':
				msg.append("&#39;");
				break;
			case '/':
				msg.append("&#47;");
				break;
			case '\n':
				msg.append(rp);
				break;
			case '©':
				msg.append("&#169;");
				break;
			case '®':
				msg.append("&#174;");
				break;
			case '×':
				msg.append("&#215;");
				break;
			case '÷':
				msg.append("&#247;");
				break;
			default:
				msg.append(c);
			}
		}
		return msg.toString();
	}

	public static String numberToChinese(int num) {
		if ((num > -1) && (num < 11)) {
			return String.valueOf(serial.charAt(num));
		}
		return String.valueOf(num);
	}

	public static String rebuildText(String text) {
		if ((text == null) || (text.length() == 0))
			return "";
		return rebuildTextWithBias(text);
	}

	public static String rebuildTextWithBias(String text) {
		StringBuffer tt = new StringBuffer(512);
		String[] tokens = text.split("//");
		for (int i = 0; i < tokens.length; i++) {
			if (i == 0)
				tt.append("<p class=\"one-dialogue\"><span>").append("说").append("</span>:").append(tokens[i]).append("</p>");
			else
				tt.append("<p class=\"one-dialogue\"><span>").append(tokens.length - i).append("</span>//").append(tokens[i]).append("</p>");
		}
		return tt.toString();
	}

	public static String encodeExportFileName(String userAgent, String name) {
		if ((userAgent == null) || (userAgent.length() == 0))
			return "empty";
		try {
			if (userAgent.indexOf("MSIE") != -1) {
				return URLEncoder.encode(name, "utf-8");
			}
			return new String(name.getBytes("utf-8"), "iso-8859-1");
		} catch (Exception e) {
		}
		return name;
	}

	public static String removeDot(String str) {
		if (str == null)
			return "";
		int p = str.indexOf('.');
		if (p > -1) {
			return str.substring(0, p);
		}
		return str;
	}

	public static void main(String[] arv) {
		System.out.println(rebuildText("//afasf asdfasd //adsfasdf adfas //asdf asf  //asdf"));
	}
}