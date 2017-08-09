package yyl.example.basic.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat 是一个以与语言环境有关的方式来格式化和解析日期的具体类。它允许进行格式化（日期 -> 文本）、解析（文本 -> 日期）和规范化
 */
public class DateFormatTest {

	public static void main(String[] args) {

		String[] patterns = { //
				"yyyy-MM-dd'T'HH:mm:ss.SSS", //
				"EEE MMM dd HH:mm:ss zzz yyyy", //
				"yyyy-MM-dd HH:mm:ss", //
				"d MMM yyyy h:m a", //
				"MM/dd/yyyy", //
				"yyyy.MM.dd G 'at' HH:mm:ss z", //2001.07.04 AD at 12:08:56 PDT  
				"EEE, MMM d, ''yy", //  Wed, Jul 4, '01  
				"h:mm a", //12:08 PM  
				"hh 'o''clock' a, zzzz", // 12 o'clock PM, Pacific Daylight Time  
				"K:mm a, z", //  0:08 PM, PDT  
				"yyyyy.MMMMM.dd GGG hh:mm aaa", //  02001.July.04 AD 12:08 PM  
				"EEE, d MMM yyyy HH:mm:ss Z", //  Wed, 4 Jul 2001 12:08:56 -0700  
				"yyMMddHHmmssZ", //010704120856-0700  
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ",//  2001-07-04T12:08:56.235-0700  
		};

		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat();
		for (String pattern : patterns) {
			formater.applyPattern(pattern);
			System.out.println(pattern + " -> " + formater.format(date));
		}
	}
}

//	字母  | 日期或时间元素  | 表示  | 示例  
//	G  Era 标志符  Text  AD  
//	y  年  Year  1996; 96  
//	M  年中的月份  Month  July; Jul; 07  
//	w  年中的周数  Number  27  
//	W  月份中的周数  Number  2  
//	D  年中的天数  Number  189  
//	d  月份中的天数  Number  10  
//	F  月份中的星期  Number  2  
//	E  星期中的天数  Text  Tuesday; Tue  
//	a  Am/pm 标记  Text  PM  
//	H  一天中的小时数（0-23）  Number  0  
//	k  一天中的小时数（1-24）  Number  24  
//	K  am/pm 中的小时数（0-11）  Number  0  
//	h  am/pm 中的小时数（1-12）  Number  12  
//	m  小时中的分钟数  Number  30  
//	s  分钟中的秒数  Number  55  
//	S  毫秒数  Number  978  
//	z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00  
//	Z  时区  RFC 822 time zone  -0800  

//	Text: 对于格式化来说，如果模式字母的数量大于等于 4，则使用完全形式；否则，在可用的情况下使用短形式或缩写形式。对于解析来说，两种形式都是可接受的，与模式字母的数量无关。 
//	Number: 对于格式化来说，模式字母的数量是最小的数位，如果数位不够，则用 0 填充以达到此数量。对于解析来说，模式字母的数量被忽略，除非必须分开两个相邻字段。 
//	Year: 如果格式器的 Calendar 是格里高利历，则应用以下规则。
//		对于格式化来说，如果模式字母的数量为 2，则年份截取为 2 位数,否则将年份解释为 number。 
//		对于解析来说，如果模式字母的数量大于 2，则年份照字面意义进行解释，而不管数位是多少。因此使用模式 "MM/dd/yyyy"，将 "01/11/12" 解析为公元 12 年 1 月 11 日。 
//		在解析缩写年份模式（"y" 或 "yy"）时，SimpleDateFormat 必须相对于某个世纪来解释缩写的年份。这通过将日期调整为 SimpleDateFormat 实例创建之前的 80 年和之后 20 年范围内来完成。例如，在 "MM/dd/yy" 模式下，如果 SimpleDateFormat 实例是在 1997 年 1 月 1 日创建的，则字符串 "01/11/12" 将被解释为 2012 年 1 月 11 日，而字符串 "05/04/64" 将被解释为 1964 年 5 月 4 日。在解析时，只有恰好由两位数字组成的字符串（如 Character.isDigit(char) 所定义的）被解析为默认的世纪。其他任何数字字符串将照字面意义进行解释，例如单数字字符串，3 个或更多数字组成的字符串，或者不都是数字的两位数字字符串（例如"-1"）。因此，在相同的模式下， "01/02/3" 或 "01/02/003" 解释为公元 3 年 1 月 2 日。同样，"01/02/-3" 解析为公元前 4 年 1 月 2 日。 
//		否则，则应用日历系统特定的形式。对于格式化和解析，如果模式字母的数量为 4 或大于 4，则使用日历特定的 long form。否则，则使用日历特定的 short or abbreviated form。 
//	Month: 如果模式字母的数量为 3 或大于 3，则将月份解释为 text；否则解释为 number。 
//	General time zone: 如果时区有名称，则将它们解释为 text。对于表示 GMT 偏移值的时区，使用以下语法： 