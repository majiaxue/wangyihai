package com.xingshi.suggestion_history;

import com.xingshi.mvp.IView;
import com.xingshi.suggestion_history.adapter.SuggestionHistoryAdapter;

public interface SuggestionHistoryView extends IView {
    void loadRv(SuggestionHistoryAdapter adapter);
}
