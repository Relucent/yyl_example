package yyl.example.demo.spell;

import java.util.Iterator;
import java.util.LinkedHashMap;

/** 汉字转化为全拼* */
@SuppressWarnings("serial")
public class CnToSpell {
	private static LinkedHashMap<String, Integer> spellMap = new LinkedHashMap<String, Integer>(400) {
		{
			put("a", -20319);
			put("ai", -20317);
			put("an", -20304);
			put("ang", -20295);
			put("ao", -20292);
			put("ba", -20283);
			put("bai", -20265);
			put("ban", -20257);
			put("bang", -20242);
			put("bao", -20230);
			put("bei", -20051);
			put("ben", -20036);
			put("beng", -20032);
			put("bi", -20026);
			put("bian", -20002);
			put("biao", -19990);
			put("bie", -19986);
			put("bin", -19982);
			put("bing", -19976);
			put("bo", -19805);
			put("bu", -19784);
			put("ca", -19775);
			put("cai", -19774);
			put("can", -19763);
			put("cang", -19756);
			put("cao", -19751);
			put("ce", -19746);
			put("ceng", -19741);
			put("cha", -19739);
			put("chai", -19728);
			put("chan", -19725);
			put("chang", -19715);
			put("chao", -19540);
			put("che", -19531);
			put("chen", -19525);
			put("cheng", -19515);
			put("chi", -19500);
			put("chong", -19484);
			put("chou", -19479);
			put("chu", -19467);
			put("chuai", -19289);
			put("chuan", -19288);
			put("chuang", -19281);
			put("chui", -19275);
			put("chun", -19270);
			put("chuo", -19263);
			put("ci", -19261);
			put("cong", -19249);
			put("cou", -19243);
			put("cu", -19242);
			put("cuan", -19238);
			put("cui", -19235);
			put("cun", -19227);
			put("cuo", -19224);
			put("da", -19218);
			put("dai", -19212);
			put("dan", -19038);
			put("dang", -19023);
			put("dao", -19018);
			put("de", -19006);
			put("deng", -19003);
			put("di", -18996);
			put("dian", -18977);
			put("diao", -18961);
			put("die", -18952);
			put("ding", -18783);
			put("diu", -18774);
			put("dong", -18773);
			put("dou", -18763);
			put("du", -18756);
			put("duan", -18741);
			put("dui", -18735);
			put("dun", -18731);
			put("duo", -18722);
			put("e", -18710);
			put("en", -18697);
			put("er", -18696);
			put("fa", -18526);
			put("fan", -18518);
			put("fang", -18501);
			put("fei", -18490);
			put("fen", -18478);
			put("feng", -18463);
			put("fo", -18448);
			put("fou", -18447);
			put("fu", -18446);
			put("ga", -18239);
			put("gai", -18237);
			put("gan", -18231);
			put("gang", -18220);
			put("gao", -18211);
			put("ge", -18201);
			put("gei", -18184);
			put("gen", -18183);
			put("geng", -18181);
			put("gong", -18012);
			put("gou", -17997);
			put("gu", -17988);
			put("gua", -17970);
			put("guai", -17964);
			put("guan", -17961);
			put("guang", -17950);
			put("gui", -17947);
			put("gun", -17931);
			put("guo", -17928);
			put("ha", -17922);
			put("hai", -17759);
			put("han", -17752);
			put("hang", -17733);
			put("hao", -17730);
			put("he", -17721);
			put("hei", -17703);
			put("hen", -17701);
			put("heng", -17697);
			put("hong", -17692);
			put("hou", -17683);
			put("hu", -17676);
			put("hua", -17496);
			put("huai", -17487);
			put("huan", -17482);
			put("huang", -17468);
			put("hui", -17454);
			put("hun", -17433);
			put("huo", -17427);
			put("ji", -17417);
			put("jia", -17202);
			put("jian", -17185);
			put("jiang", -16983);
			put("jiao", -16970);
			put("jie", -16942);
			put("jin", -16915);
			put("jing", -16733);
			put("jiong", -16708);
			put("jiu", -16706);
			put("ju", -16689);
			put("juan", -16664);
			put("jue", -16657);
			put("jun", -16647);
			put("ka", -16474);
			put("kai", -16470);
			put("kan", -16465);
			put("kang", -16459);
			put("kao", -16452);
			put("ke", -16448);
			put("ken", -16433);
			put("keng", -16429);
			put("kong", -16427);
			put("kou", -16423);
			put("ku", -16419);
			put("kua", -16412);
			put("kuai", -16407);
			put("kuan", -16403);
			put("kuang", -16401);
			put("kui", -16393);
			put("kun", -16220);
			put("kuo", -16216);
			put("la", -16212);
			put("lai", -16205);
			put("lan", -16202);
			put("lang", -16187);
			put("lao", -16180);
			put("le", -16171);
			put("lei", -16169);
			put("leng", -16158);
			put("li", -16155);
			put("lia", -15959);
			put("lian", -15958);
			put("liang", -15944);
			put("liao", -15933);
			put("lie", -15920);
			put("lin", -15915);
			put("ling", -15903);
			put("liu", -15889);
			put("long", -15878);
			put("lou", -15707);
			put("lu", -15701);
			put("lv", -15681);
			put("luan", -15667);
			put("lue", -15661);
			put("lun", -15659);
			put("luo", -15652);
			put("ma", -15640);
			put("mai", -15631);
			put("man", -15625);
			put("mang", -15454);
			put("mao", -15448);
			put("me", -15436);
			put("mei", -15435);
			put("men", -15419);
			put("meng", -15416);
			put("mi", -15408);
			put("mian", -15394);
			put("miao", -15385);
			put("mie", -15377);
			put("min", -15375);
			put("ming", -15369);
			put("miu", -15363);
			put("mo", -15362);
			put("mou", -15183);
			put("mu", -15180);
			put("na", -15165);
			put("nai", -15158);
			put("nan", -15153);
			put("nang", -15150);
			put("nao", -15149);
			put("ne", -15144);
			put("nei", -15143);
			put("nen", -15141);
			put("neng", -15140);
			put("ni", -15139);
			put("nian", -15128);
			put("niang", -15121);
			put("niao", -15119);
			put("nie", -15117);
			put("nin", -15110);
			put("ning", -15109);
			put("niu", -14941);
			put("nong", -14937);
			put("nu", -14933);
			put("nv", -14930);
			put("nuan", -14929);
			put("nue", -14928);
			put("nuo", -14926);
			put("o", -14922);
			put("ou", -14921);
			put("pa", -14914);
			put("pai", -14908);
			put("pan", -14902);
			put("pang", -14894);
			put("pao", -14889);
			put("pei", -14882);
			put("pen", -14873);
			put("peng", -14871);
			put("pi", -14857);
			put("pian", -14678);
			put("piao", -14674);
			put("pie", -14670);
			put("pin", -14668);
			put("ping", -14663);
			put("po", -14654);
			put("pu", -14645);
			put("qi", -14630);
			put("qia", -14594);
			put("qian", -14429);
			put("qiang", -14407);
			put("qiao", -14399);
			put("qie", -14384);
			put("qin", -14379);
			put("qing", -14368);
			put("qiong", -14355);
			put("qiu", -14353);
			put("qu", -14345);
			put("quan", -14170);
			put("que", -14159);
			put("qun", -14151);
			put("ran", -14149);
			put("rang", -14145);
			put("rao", -14140);
			put("re", -14137);
			put("ren", -14135);
			put("reng", -14125);
			put("ri", -14123);
			put("rong", -14122);
			put("rou", -14112);
			put("ru", -14109);
			put("ruan", -14099);
			put("rui", -14097);
			put("run", -14094);
			put("ruo", -14092);
			put("sa", -14090);
			put("sai", -14087);
			put("san", -14083);
			put("sang", -13917);
			put("sao", -13914);
			put("se", -13910);
			put("sen", -13907);
			put("seng", -13906);
			put("sha", -13905);
			put("shai", -13896);
			put("shan", -13894);
			put("shang", -13878);
			put("shao", -13870);
			put("she", -13859);
			put("shen", -13847);
			put("sheng", -13831);
			put("shi", -13658);
			put("shou", -13611);
			put("shu", -13601);
			put("shua", -13406);
			put("shuai", -13404);
			put("shuan", -13400);
			put("shuang", -13398);
			put("shui", -13395);
			put("shun", -13391);
			put("shuo", -13387);
			put("si", -13383);
			put("song", -13367);
			put("sou", -13359);
			put("su", -13356);
			put("suan", -13343);
			put("sui", -13340);
			put("sun", -13329);
			put("suo", -13326);
			put("ta", -13318);
			put("tai", -13147);
			put("tan", -13138);
			put("tang", -13120);
			put("tao", -13107);
			put("te", -13096);
			put("teng", -13095);
			put("ti", -13091);
			put("tian", -13076);
			put("tiao", -13068);
			put("tie", -13063);
			put("ting", -13060);
			put("tong", -12888);
			put("tou", -12875);
			put("tu", -12871);
			put("tuan", -12860);
			put("tui", -12858);
			put("tun", -12852);
			put("tuo", -12849);
			put("wa", -12838);
			put("wai", -12831);
			put("wan", -12829);
			put("wang", -12812);
			put("wei", -12802);
			put("wen", -12607);
			put("weng", -12597);
			put("wo", -12594);
			put("wu", -12585);
			put("xi", -12556);
			put("xia", -12359);
			put("xian", -12346);
			put("xiang", -12320);
			put("xiao", -12300);
			put("xie", -12120);
			put("xin", -12099);
			put("xing", -12089);
			put("xiong", -12074);
			put("xiu", -12067);
			put("xu", -12058);
			put("xuan", -12039);
			put("xue", -11867);
			put("xun", -11861);
			put("ya", -11847);
			put("yan", -11831);
			put("yang", -11798);
			put("yao", -11781);
			put("ye", -11604);
			put("yi", -11589);
			put("yin", -11536);
			put("ying", -11358);
			put("yo", -11340);
			put("yong", -11339);
			put("you", -11324);
			put("yu", -11303);
			put("yuan", -11097);
			put("yue", -11077);
			put("yun", -11067);
			put("za", -11055);
			put("zai", -11052);
			put("zan", -11045);
			put("zang", -11041);
			put("zao", -11038);
			put("ze", -11024);
			put("zei", -11020);
			put("zen", -11019);
			put("zeng", -11018);
			put("zha", -11014);
			put("zhai", -10838);
			put("zhan", -10832);
			put("zhang", -10815);
			put("zhao", -10800);
			put("zhe", -10790);
			put("zhen", -10780);
			put("zheng", -10764);
			put("zhi", -10587);
			put("zhong", -10544);
			put("zhou", -10533);
			put("zhu", -10519);
			put("zhua", -10331);
			put("zhuai", -10329);
			put("zhuan", -10328);
			put("zhuang", -10322);
			put("zhui", -10315);
			put("zhun", -10309);
			put("zhuo", -10307);
			put("zi", -10296);
			put("zong", -10281);
			put("zou", -10274);
			put("zu", -10270);
			put("zuan", -10262);
			put("zui", -10260);
			put("zun", -10256);
			put("zuo", -10254);
		}
	};

	private CnToSpell() {
	}

	/**
	 * 获得单个汉字的Ascii. 错误返回 0,否则返回ascii
	 */
	public static int getCnAscii(char cn) {
		byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes == null) {
			return 0;
		}
		if (bytes.length == 1) { //英文字符
			return bytes[0];
		}
		if (bytes.length == 2) { //中文字符
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			return ascii;
		}
		return 0; //错误
	}

	/**
	 * 根据ASCII码到SpellMap中查找对应的拼音
	 * @param ascii int 字符对应的ASCII
	 * @return String 拼音,首先判断ASCII是否>0&<160,如果是返回对应的字符,
	 *         否则到SpellMap中查找,如果没有找到拼音,则返回null,如果找到则返回拼音.
	 */
	public static String getSpellByAscii(int ascii) {
		if (ascii > 0 && ascii < 160) { //单字符
			return String.valueOf((char) ascii);
		}

		if (ascii < -20319 || ascii > -10247) { //不知道的字符
			return null;
		}

		Iterator<String> it = spellMap.keySet().iterator();

		String spell0 = null;
		;
		String spell = null;

		int asciiRang0 = -20319;
		int asciiRang;
		while (it.hasNext()) {

			spell = (String) it.next();
			Object valObj = spellMap.get(spell);
			if (valObj instanceof Integer) {
				asciiRang = ((Integer) valObj).intValue();

				if (ascii >= asciiRang0 && ascii < asciiRang) { //区间找到
					return (spell0 == null) ? spell : spell0;
				} else {
					spell0 = spell;
					asciiRang0 = asciiRang;
				}
			}
		}

		return null;

	}

	/**
	 * 返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换
	 * @param cnStr String 字符串
	 * @return String 转换成全拼后的字符串
	 */
	public static String getFullSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}

		char[] chars = cnStr.toCharArray();
		StringBuffer sbr = new StringBuffer();
		for (int i = 0, Len = chars.length; i < Len; i++) {
			int ascii = getCnAscii(chars[i]);
			if (ascii == 0) { //取ascii时出错
				sbr.append(chars[i]);
			} else {
				String spell = getSpellByAscii(ascii);
				if (spell == null) {
					sbr.append(chars[i]);
				} else {
					sbr.append(spell);
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for

		return sbr.toString();
	}

	public static void main(String[] args) {
		String str = null;
		str = "字符串的全拼";
		System.out.println("Spell=" + CnToSpell.getFullSpell(str));

		char c = '你';
		System.out.println((int) c + ":" + CnToSpell.getCnAscii(c));

	}
}
