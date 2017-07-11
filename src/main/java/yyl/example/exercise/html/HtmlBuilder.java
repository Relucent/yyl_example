package yyl.example.exercise.html;

import java.io.StringWriter;

/**
 * HTML构建者，用于构建一个HTML片段
 */
public class HtmlBuilder {

	// ==============================Fields============================================
	private final StringWriter writer;

	// ==============================Constructors======================================

	/**
	 * 默认构造函数
	 */
	public HtmlBuilder() {
		this.writer = new StringWriter();
	}

	/**
	 * 默认构造函数
	 */
	public HtmlBuilder(StringWriter writer) {
		this.writer = writer;
	}

	// ==============================Methods===========================================
	/**
	 * 返回HTML字符串。
	 */
	public String toString() {
		return writer.toString();
	}

	/**
	 * 追加 Object 参数的字符串表示形式
	 */
	public HtmlBuilder append(Object text) {
		if (text != null) {
			writer.append(text.toString());
		}
		return this;
	}

	/**
	 * 追加一个字符串
	 */
	public HtmlBuilder append(String text) {
		if (text != null) {
			writer.append(text);
		}
		return this;
	}

	/**
	 * 追加制表符[\t]
	 * @param 需要追加的制表符个数
	 */
	public HtmlBuilder tabs(int tabs) {
		for (int i = 0; i < tabs; i++) {
			tab();
		}
		return this;
	}

	/**
	 * 追加换行符[\n]
	 * @param 需要追加新行数
	 */
	public HtmlBuilder newlines(int newlines) {
		for (int i = 0; i < newlines; i++) {
			newline();
		}
		return this;
	}

	/**
	 * 追加制表符[\t]
	 */
	public HtmlBuilder tab() {
		append("\t");
		return this;
	}

	/**
	 * 追加换行符[\n]
	 */
	public HtmlBuilder newline() {
		append("\n");
		return this;
	}

	/**
	 * 关闭页面元素 [>]
	 */
	public HtmlBuilder close() {
		append(">");
		return this;
	}

	/**
	 * 关闭页面元素，追加斜线的 [/>]
	 */
	public HtmlBuilder xclose() {
		append("/>");
		return this;
	}

	/**
	 * 开始一个form标记
	 */
	public HtmlBuilder form() {
		append("<form");
		return this;
	}

	/**
	 * 关闭一个form标记
	 */
	public HtmlBuilder formEnd() {
		append("</form>");
		return this;
	}

	/**
	 * 开始一个table标记
	 */
	public HtmlBuilder table() {
		append("<table");
		return this;
	}

	/**
	 * 关闭一个table标记
	 */
	public HtmlBuilder tableEnd() {
		append("</table>");
		return this;
	}

	/**
	 * 开始一个tbody标记
	 */
	public HtmlBuilder tbody() {
		append("<tbody");
		return this;
	}

	/**
	 * 关闭一个tbody标记
	 */
	public HtmlBuilder tbodyEnd() {
		append("</tbody>");
		return this;
	}

	/**
	 * 开始一个thead标记
	 */
	public HtmlBuilder thead() {
		append("<thead");
		return this;
	}

	/**
	 * 关闭一个thead标记
	 */
	public HtmlBuilder theadEnd() {
		append("</thead>");
		return this;
	}

	/**
	 * 开始一个tr标记
	 */
	public HtmlBuilder tr() {
		append("<tr");
		return this;
	}

	/**
	 * 关闭一个tr标记
	 */
	public HtmlBuilder trEnd() {
		append("</tr>");
		return this;
	}

	/**
	 * 开始一个th标记
	 */
	public HtmlBuilder th() {
		append("<th");
		return this;
	}

	/**
	 * 关闭一个th标记
	 */
	public HtmlBuilder thEnd() {
		append("</th>");
		return this;
	}

	/**
	 * 开始一个td标记
	 */
	public HtmlBuilder td() {
		append("<td");
		return this;
	}

	/**
	 * 关闭一个td标记
	 */
	public HtmlBuilder tdEnd() {
		append("</td>");
		return this;
	}

	/**
	 * 开始一个input标记
	 * @param type 类型[<input type=]
	 * @return
	 */
	public HtmlBuilder input(String type) {
		append("<input type=\"").append(type).append("\" ");
		return this;
	}

	/**
	 * 开始一个select标记
	 */
	public HtmlBuilder select() {
		append("<select");

		return this;
	}

	/**
	 * 关闭一个select标记
	 */
	public HtmlBuilder selectEnd() {
		append("</select>");

		return this;
	}

	/**
	 * 开始一个option标记 [<option]
	 */
	public HtmlBuilder option() {
		append("<option");

		return this;
	}

	/**
	 * 关闭一个option标记 [</option>]
	 */
	public HtmlBuilder optionEnd() {
		append("</option>");
		return this;
	}

	public HtmlBuilder append(String name, Object value) {
		if (isNotBlank(name)) {
			append(" ").append(name).append("=\"").append(value != null ? value.toString() : "").append("\" ");
		}
		return this;
	}

	/**
	 * <p>
	 * The name attribute [name=].
	 * </p>
	 */
	public HtmlBuilder name(String name) {
		if (isNotBlank(name)) {
			append(" name=\"").append(name).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The value attribute [value=].
	 * </p>
	 * 
	 * <p>
	 * If the value parameter is null or empty then will append a empty value element.
	 * </p>
	 */
	public HtmlBuilder value(String value) {
		if (isNotBlank(value)) {
			append(" value=\"").append(value).append("\" ");
		} else {
			append(" value=\"").append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The title attribute [title=].
	 * </p>
	 */
	public HtmlBuilder title(String title) {
		if (isNotBlank(title)) {
			append(" title=\"").append(title).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The action attribute [action=].
	 * </p>
	 */
	public HtmlBuilder action(String action) {
		append(" action=\"");
		if (isNotBlank(action)) {
			append(action);
		}
		append("\" ");

		return this;
	}

	/**
	 * <p>
	 * The method attribute [method=].
	 * </p>
	 */
	public HtmlBuilder method(String method) {
		if (isNotBlank(method)) {
			append(" method=\"").append(method).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The enctype attribute [enctype=].
	 * </p>
	 */
	public HtmlBuilder enctype(String enctype) {
		if (isNotBlank(enctype)) {
			append(" enctype=\"").append(enctype).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onchange attribute [onchange=].
	 * </p>
	 */
	public HtmlBuilder onchange(String onchange) {
		if (isNotBlank(onchange)) {
			append(" onchange=\"").append(onchange).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onsubmit attribute [onsubmit=].
	 * </p>
	 */
	public HtmlBuilder onsubmit(String onsubmit) {
		if (isNotBlank(onsubmit)) {
			append(" onsubmit=\"").append(onsubmit).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onclick attribute [onclick=].
	 * </p>
	 */
	public HtmlBuilder onclick(String onclick) {
		if (isNotBlank(onclick)) {
			append(" onclick=\"").append(onclick).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onmouseover attribute [onmouseover=].
	 * </p>
	 */
	public HtmlBuilder onmouseover(String onmouseover) {
		if (isNotBlank(onmouseover)) {
			append(" onmouseover=\"").append(onmouseover).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onmouseout attribute [onmouseout=].
	 * </p>
	 */
	public HtmlBuilder onmouseout(String onmouseout) {
		if (isNotBlank(onmouseout)) {
			append(" onmouseout=\"").append(onmouseout).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The onkeypress attribute [onkeypress=].
	 * </p>
	 */
	public HtmlBuilder onkeypress(String onkeypress) {
		if (isNotBlank(onkeypress)) {
			append(" onkeypress=\"").append(onkeypress).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The id attribute [id=].
	 * </p>
	 */
	public HtmlBuilder id(String id) {
		if (isNotBlank(id)) {
			append(" id=\"").append(id).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The class attribute [class=].
	 * </p>
	 */
	public HtmlBuilder styleClass(String styleClass) {
		if (isNotBlank(styleClass)) {
			append(" class=\"").append(styleClass).append("\" ");
		}
		return this;
	}

	/**
	 * <p>
	 * The style attribute [style=].
	 * </p>
	 */
	public HtmlBuilder style(String style) {
		if (isNotBlank(style)) {
			append(" style=\"").append(style).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The width attribute [width=].
	 * </p>
	 */
	public HtmlBuilder width(String width) {
		if (isNotBlank(width)) {
			append(" width=\"").append(width).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The align attribute [align=].
	 * </p>
	 */
	public HtmlBuilder align(String align) {
		if (isNotBlank(align)) {
			append(" align=\"").append(align).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The valign attribute [valign=].
	 * </p>
	 */
	public HtmlBuilder valign(String valign) {
		if (isNotBlank(valign)) {
			append(" valign=\"").append(valign).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The border attribute [border=].
	 * </p>
	 */
	public HtmlBuilder border(String border) {
		if (isNotBlank(border)) {
			append(" border=\"").append(border).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The cellpadding attribute [cellpadding=].
	 * </p>
	 */
	public HtmlBuilder cellPadding(String cellPadding) {
		if (isNotBlank(cellPadding)) {
			append(" cellpadding=\"").append(cellPadding).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The cellspacing attribute [cellspacing=].
	 * </p>
	 */
	public HtmlBuilder cellSpacing(String cellSpacing) {
		if (isNotBlank(cellSpacing)) {
			append(" cellspacing=\"").append(cellSpacing).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The colspan attribute [colspan=].
	 * </p>
	 */
	public HtmlBuilder colSpan(String colspan) {
		if (isNotBlank(colspan)) {
			append(" colspan=\"").append(colspan).append("\" ");
		}
		return this;
	}

	/**
	 * <p>
	 * The rowspan attribute [rowspan=].
	 * </p>
	 */
	public HtmlBuilder rowSpan(String rowspan) {
		if (isNotBlank(rowspan)) {
			append(" rowspan=\"").append(rowspan).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The size attribute [size=].
	 * </p>
	 */
	public HtmlBuilder size(String size) {
		if (isNotBlank(size)) {
			append(" size=\"").append(size).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The start of the span element [<span].
	 * </p>
	 */
	public HtmlBuilder span() {
		append("<span");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the span element [</span>].
	 * </p>
	 */
	public HtmlBuilder spanEnd() {
		append("</span>");

		return this;
	}

	/**
	 * <p>
	 * The start of the div element [<div].
	 * </p>
	 */
	public HtmlBuilder div() {
		append("<div");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the div element [</div>].
	 * </p>
	 */
	public HtmlBuilder divEnd() {
		append("</div>");

		return this;
	}

	/**
	 * <p>
	 * A URL parameter name/value [name=value]
	 * </p>
	 */
	public HtmlBuilder param(String name, String value) {
		append(name);
		equals();
		append(value);

		return this;
	}

	/**
	 * <p>
	 * The start of the a element plus the href attribute [<a href=].
	 * </p>
	 */
	public HtmlBuilder a(String href) {
		append("<a href=");
		quote();
		append(href);
		quote();

		return this;
	}

	/**
	 * <p>
	 * The start of the a element plus the href attribute [<a href=].
	 * </p>
	 */
	public HtmlBuilder a() {
		append("<a href=");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the a element [</div>].
	 * </p>
	 */
	public HtmlBuilder aEnd() {
		append("</a>");

		return this;
	}

	/**
	 * <p>
	 * The bold element [<b>].
	 * </p>
	 */
	public HtmlBuilder bold() {
		append("<b>");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the bold element [</b>].
	 * </p>
	 */
	public HtmlBuilder boldEnd() {
		append("</b>");

		return this;
	}

	/**
	 * <p>
	 * A single quote ["].
	 * </p>
	 */
	public HtmlBuilder quote() {
		append("\"");

		return this;
	}

	/**
	 * <p>
	 * A single question mark [?].
	 * </p>
	 */
	public HtmlBuilder question() {
		append("?");

		return this;
	}

	/**
	 * <p>
	 * A single equals [=].
	 * </p>
	 */
	public HtmlBuilder equals() {
		append("=");

		return this;
	}

	/**
	 * <p>
	 * A single ampersand [&].
	 * </p>
	 */
	public HtmlBuilder ampersand() {
		append("&");

		return this;
	}

	/**
	 * <p>
	 * The start of the img element [<img].
	 * </p>
	 */
	public HtmlBuilder img() {
		append("<img");

		return this;
	}

	/**
	 * <p>
	 * The src attribute [src=].
	 * </p>
	 */
	public HtmlBuilder src(String src) {
		if (isNotBlank(src)) {
			append(" src=\"").append(src).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The alt attribute [alt=].
	 * </p>
	 */
	public HtmlBuilder alt(String alt) {
		if (isNotBlank(alt)) {
			append(" alt=\"").append(alt).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The start of the src element plus the src attribute [<img src= style=border:0/>].
	 * </p>
	 */
	public HtmlBuilder img(String src) {
		append("<img src=\"").append(src).append("\" style=\"border:0\"/> ");

		return this;
	}

	/**
	 * <p>
	 * The start of the src element plus the src attribute [<img src="" tooltip="" style="border:0">].
	 * </p>
	 */
	public HtmlBuilder img(String img, String tooltip) {
		append("<img src=\"").append(img).append("\" style=\"border:0\"");

		if (tooltip != null) {
			append(" title=\"").append(tooltip).append("\">");
		}

		return this;
	}

	/**
	 * <p>
	 * The start of the textarea element [<textarea].
	 * </p>
	 */
	public HtmlBuilder textarea() {
		append("<textarea");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the textarea element [</textarea>].
	 * </p>
	 */
	public HtmlBuilder textareaEnd() {
		append("</textarea>");

		return this;
	}

	/**
	 * <p>
	 * The cols attribute [cols=].
	 * </p>
	 */
	public HtmlBuilder cols(String cols) {
		if (isNotBlank(cols)) {
			append(" cols=\"").append(cols).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The rows attribute [rows=].
	 * </p>
	 */
	public HtmlBuilder rows(String rows) {
		if (isNotBlank(rows)) {
			append(" rows=\"").append(rows).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The checked attribute [checked="checked"].
	 * </p>
	 */
	public HtmlBuilder checked() {
		append(" checked=\"checked\"");

		return this;
	}

	/**
	 * <p>
	 * The selected attribute [selected="selected"].
	 * </p>
	 */
	public HtmlBuilder selected() {
		append(" selected=\"selected\"");

		return this;
	}

	/**
	 * <p>
	 * The readonly attribute [readonly="readonly"].
	 * </p>
	 */
	public HtmlBuilder readonly() {
		append(" readonly=\"readonly\"");

		return this;
	}

	/**
	 * <p>
	 * The non-breaking space [&nbsp;].
	 * </p>
	 */
	public HtmlBuilder nbsp() {
		append("&#160;");

		return this;
	}

	/**
	 * <p>
	 * The comment [<!-- -->].
	 * </p>
	 */
	public HtmlBuilder comment(String comment) {
		if (isNotBlank(comment)) {
			append(" <!-- ").append(comment).append(" -->");
		}

		return this;
	}

	/**
	 * <p>
	 * The ul element [
	 * <ul>
	 * ].
	 * </p>
	 */
	public HtmlBuilder ul() {
		append("<ul>");

		return this;
	}

	/**
	 * <p>
	 * The close tag of the ul element [
	 * </ul>
	 * ].
	 * </p>
	 */
	public HtmlBuilder ulEnd() {
		append("</ul>");

		return this;
	}

	/**
	 * <p>
	 * The li element [
	 * <li></li>].
	 * </p>
	 */
	public HtmlBuilder li(String text) {
		if (isNotBlank(text)) {
			append("<li>").append(text).append("</li>");
		}

		return this;
	}

	/**
	 * <p>
	 * The br element [<br/>
	 * ].
	 * </p>
	 */
	public HtmlBuilder br() {
		append("<br/>");

		return this;
	}

	/**
	 * <p>
	 * The disabled attribute [disabled="disabled"].
	 * </p>
	 */
	public HtmlBuilder disabled() {
		append(" disabled=\"disabled\" ");

		return this;
	}

	/**
	 * <p>
	 * The nowrap attribute [nowrap="nowrap"].
	 * </p>
	 */
	public HtmlBuilder nowrap() {
		append(" nowrap=\"nowrap\" ");

		return this;
	}

	/**
	 * <p>
	 * The maxlength attribute [maxlength=].
	 * </p>
	 */
	public HtmlBuilder maxlength(String maxlength) {
		if (isNotBlank(maxlength)) {
			append(" maxlength=\"").append(maxlength).append("\" ");
		}

		return this;
	}

	/**
	 * <p>
	 * The start of the p element [<p].
	 * </p>
	 */
	public HtmlBuilder p() {
		append("<p");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the p element [
	 * </p>
	 * ].
	 * </p>
	 */
	public HtmlBuilder pEnd() {
		append("</p>");
		return this;
	}

	/**
	 * <p>
	 * The start of the h1 element [<h1].
	 * </p>
	 */
	public HtmlBuilder h1() {
		append("<h1");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the h1 element [</h1>].
	 * </p>
	 */
	public HtmlBuilder h1End() {
		append("</h1>");
		return this;
	}

	/**
	 * <p>
	 * The start of the h2 element [<h2].
	 * </p>
	 */
	public HtmlBuilder h2() {
		append("<h2");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the h2 element [</h2>].
	 * </p>
	 */
	public HtmlBuilder h2End() {
		append("</h2>");
		return this;
	}

	/**
	 * <p>
	 * The start of the h3 element [<h3].
	 * </p>
	 */
	public HtmlBuilder h3() {
		append("<h3");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the h3 element [</h3>].
	 * </p>
	 */
	public HtmlBuilder h3End() {
		append("</h3>");
		return this;
	}

	/**
	 * <p>
	 * The start of the h4 element [<h4].
	 * </p>
	 */
	public HtmlBuilder h4() {
		append("<h4");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the h4 element [</h4>].
	 * </p>
	 */
	public HtmlBuilder h4End() {
		append("</h4>");
		return this;
	}

	/**
	 * <p>
	 * The start of the h5 element [<h5].
	 * </p>
	 */
	public HtmlBuilder h5() {
		append("<h5");
		return this;
	}

	/**
	 * <p>
	 * The close tag of the h5 element [</h5>].
	 * </p>
	 */
	public HtmlBuilder h5End() {
		append("</h5>");
		return this;
	}

	private boolean isNotBlank(String value) {
		return value != null && value.length() > 0;
	}
}