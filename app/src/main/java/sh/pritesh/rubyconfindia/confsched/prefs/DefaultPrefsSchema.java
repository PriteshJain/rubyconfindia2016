package sh.pritesh.rubyconfindia.confsched.prefs;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "sh.pritesh.rubyconfindia.confsched_preferences")
public abstract class DefaultPrefsSchema {
    @Key("current_language_id")
    String languageId;
    @Key("notification_setting")
    boolean notificationFlag;
    @Key("heads_up_setting")
    boolean headsUpFlag;
    @Key("show_local_time")
    boolean showLocalTimeFlag;
}
