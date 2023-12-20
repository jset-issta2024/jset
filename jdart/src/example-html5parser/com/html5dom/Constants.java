package com.html5dom;

public class Constants {
	public static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
	public static final String MathML_NAMESPACE = "http://www.w3.org/1998/Math/MathML";
	public static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";

	public static final String[] FORMATTING_ELEMENTS = { "a", "b", "big",
			"code", "em", "font", "i", "nobr", "s", "small", "strike",
			"strong", "tt", "u" };
	public static final String[] SPECIAL_HTML_ELEMENTS = { "address",
			"applet", "area", "article", "aside", "base", "basefont",
			"bgsound", "blockquote", "body", "br", "button", "caption",
			"center", "col", "colgroup", "dd", "details", "dir", "div", "dl",
			"dt", "embed", "fieldset", "figcaption", "figure", "footer",
			"form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6",
			"head", "header", "hgroup", "hr", "html", "iframe", "img", "input",
			"isindex", "li", "link", "listing", "main", "marquee", "meta",
			"nav", "noembed", "noframes", "noscript", "object", "ol", "p",
			"param", "plaintext", "pre", "script", "section", "select",
			"source", "style", "summary", "table", "tbody", "td", "template",
			"textarea", "tfoot", "th", "thead", "title", "tr", "track", "ul",
			"wbr", "xmp" };
	public static final String[] SPECIAL_MATHML_ELEMENTS = { "mi", "mo",
			"mn", "ms", "mtext", "annotation-xml" };
	public static final String[] SPECIAL_SVG_ELEMENTS = { "foreignObject",
			"desc", "title" };

	public static final String[] VOID_ELEMENTS = { "area", "base", "br",
			"col", "embed", "hr", "img", "input", "keygen", "link", "meta",
			"param", "source", "track", "wbr" };
	public static final String[] RAW_TEXT_ELEMENTS = { "script", "style" };
	public static final String[] ESCAPABLE_RAW_TEXT_ELEMENTS = { "textarea",
			"title" };

	public static final String[] HTML5_SEMANTIC_STRUCTURAL_ELEMENTS = { "article", "aside",
			"bdi", "details", "dialog", "figcaption", "figure", "footer",
			"header", "main", "mark", "menuitem", "meter", "nav", "progress",
			"rp", "rt", "ruby", "section", "summary", "time", "wbr" };
	
	public static final String[] HTML5_FORM_ELEMENTS = {"datalist", "keygen", "output"};
	
	public static final String[] HTML5_GRAPHIC_ELEMENTS = {"canvas", "svg"};
	
	public static final String[] HTML5_MEDIA_ELEMENTS = {"audio", "embed", "source", "track", "video"};
}