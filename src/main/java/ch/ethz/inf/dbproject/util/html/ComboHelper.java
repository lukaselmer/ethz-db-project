package ch.ethz.inf.dbproject.util.html;

import java.util.List;
import ch.ethz.inf.dbproject.model.ComboInterface;

public final class ComboHelper extends HtmlHelperIface {
	
	private final String name;
	private final List<ComboInterface> items;
	
	public ComboHelper (String name, List<ComboInterface> items) {
		super();
		this.name = name;
		this.items = items;
	}

	@Override
	public final String generateHtmlCode() {
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append("<!-- ComboHelper Generated Code -->\n");
		sb.append("<select name=\""+ name + "\">\n");
		for (ComboInterface item : items) {
			sb.append("\t<option value=\""+ item.getId() +"\">"+ item.toString() +"</option>\n");
		}
		sb.append("</select>\n");
		
		return sb.toString();
	}
	
}
