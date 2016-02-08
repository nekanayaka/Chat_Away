package net.ddns.nimna.chat_away_v2;

import net.ddns.nimna.chat_away_v2.Model.User;

/**
 * Created by jmaceachern5567 on 2/8/2016.
 */
public interface AsyncResponse {
    void done(User user);
}
