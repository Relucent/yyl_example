package yyl.example.exercise.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * LDAP条目项实体类
 * @version 2012-10-13 1.0
 */
public class LdapEntry {

	private final String dn;
	private final Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();

	public LdapEntry(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void put(String key, Object value) {
		if (value != null) {
			List<Object> values = new ArrayList<Object>();
			values.add(value);
			attributes.put(convertKey(key), values);
		}
	}

	public void put(String key, Number value) {
		if (value != null) {
			put(key, value.toString());
		}
	}

	public void putAll(String key, List<Object> values) {
		attributes.put(convertKey(key), values);
	}

	public Object get(String key) {
		List<Object> values = getAll(key);
		return values == null || values.isEmpty() ? null : values.get(0);
	}

	public List<Object> getAll(String key) {
		return attributes.get(convertKey(key));
	}

	public void remove(String key) {
		attributes.remove(convertKey(key));
	}

	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	public Set<String> keySet() {
		return attributes.keySet();
	}

	protected String convertKey(Object key) {
		if (key != null) {
			return key.toString().toLowerCase();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "dn:" + dn;
	}
}
