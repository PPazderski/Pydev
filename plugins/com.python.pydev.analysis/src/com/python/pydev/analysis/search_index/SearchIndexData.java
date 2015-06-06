/**
 * Copyright (c) 20015 by Brainwy Software Ltda. All Rights Reserved.
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.python.pydev.analysis.search_index;

import org.eclipse.jface.dialogs.IDialogSettings;

public class SearchIndexData {

    private static final String STORE_SCOPE_DATA = "scopeData";

    private static final String STORE_SCOPE = "scope";

    private static final String STORE_IS_CASE_SENSITIVE = "isCaseSensitive";

    private static final String STORE_TEXT_PATTERN = "textPattern";

    public final String textPattern;

    public final boolean isCaseSensitive;

    public static final int SCOPE_MODULES = 0;
    public static final int SCOPE_PROJECTS = 1;
    public static final int SCOPE_WORKSPACE = 2;
    public static final int SCOPE_OPEN_EDITORS = 3;
    public static final int SCOPE_EXTERNAL_FOLDERS = 4;
    public static final int MAX_SCOPE = SCOPE_EXTERNAL_FOLDERS;

    public final int scope;

    /**
     * Comma-separated list with the data related to the scope (i.e.: project names, module names or external folders).
     */
    public final String scopeData;

    public SearchIndexData(String textPattern, boolean isCaseSensitive, int scope, String scopeData) {
        if (textPattern == null) {
            textPattern = "";
        }
        this.textPattern = textPattern;
        this.isCaseSensitive = isCaseSensitive;
        this.scope = scope;
        if (scope < 0 || scope > MAX_SCOPE) {
            scope = 0;
        }

        if (scopeData == null) {
            scopeData = "";
        }
        this.scopeData = scopeData;
    }

    public void store(IDialogSettings settings) {
        settings.put(STORE_TEXT_PATTERN, textPattern);
        settings.put(STORE_IS_CASE_SENSITIVE, isCaseSensitive);
        settings.put(STORE_SCOPE, scope);
        settings.put(STORE_SCOPE_DATA, scopeData);
    }

    public static SearchIndexData create(IDialogSettings settings) {
        String textPattern = settings.get(STORE_TEXT_PATTERN);

        try {
            boolean isCaseSensitive = settings.getBoolean(STORE_IS_CASE_SENSITIVE);
            int scope = settings.getInt(STORE_SCOPE);
            String scopeData = settings.get(STORE_SCOPE_DATA);

            return new SearchIndexData(textPattern, isCaseSensitive, scope, scopeData);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isCaseSensitive ? 1231 : 1237);
        result = prime * result + scope;
        result = prime * result + ((scopeData == null) ? 0 : scopeData.hashCode());
        result = prime * result + ((textPattern == null) ? 0 : textPattern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SearchIndexData other = (SearchIndexData) obj;
        if (isCaseSensitive != other.isCaseSensitive) {
            return false;
        }
        if (scope != other.scope) {
            return false;
        }
        if (scopeData == null) {
            if (other.scopeData != null) {
                return false;
            }
        } else if (!scopeData.equals(other.scopeData)) {
            return false;
        }
        if (textPattern == null) {
            if (other.textPattern != null) {
                return false;
            }
        } else if (!textPattern.equals(other.textPattern)) {
            return false;
        }
        return true;
    }

    public ScopeAndData getScopeAndData() {
        return new ScopeAndData(this.scope, this.scopeData);
    }
}