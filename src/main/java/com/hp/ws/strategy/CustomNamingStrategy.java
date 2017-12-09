package com.hp.ws.strategy;

import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:05:54
 */
public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	private static final long serialVersionUID = -4241120763857248595L;
	public static final String TABLE_PREFIX = "HP_";

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
		if (name != null) {
			name = Identifier.toIdentifier(addUnderscores(name.getText()));
		}
		return super.toPhysicalCatalogName(name, context);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		name = Identifier.toIdentifier(addUnderscores(name.getText()));
		return super.toPhysicalSchemaName(name, context);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		name = Identifier.toIdentifier(addUnderscores(name.getText()));
		return super.toPhysicalColumnName(name, context);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		name = Identifier.toIdentifier(addUnderscores(name.getText()));
		return super.toPhysicalSequenceName(name, context);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		name = Identifier.toIdentifier(addTablePrefix(addUnderscores(name.getText())));
		return super.toPhysicalTableName(name, context);
	}

	private String addTablePrefix(final String composedTableName) {
		return TABLE_PREFIX + composedTableName.toUpperCase(Locale.ENGLISH);
	}

	protected static String addUnderscores(String name) {
		StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toUpperCase(Locale.ENGLISH);
	}

}
