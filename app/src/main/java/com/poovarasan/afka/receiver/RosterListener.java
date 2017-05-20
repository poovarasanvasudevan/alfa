package com.poovarasan.afka.receiver;

import org.jivesoftware.smack.packet.Presence;
import org.jxmpp.jid.Jid;

import java.util.Collection;

/**
 * Created by poovarasanv on 16/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 16/5/17 at 11:42 AM
 */

public class RosterListener implements org.jivesoftware.smack.roster.RosterListener {

    @Override
    public void entriesAdded(Collection<Jid> addresses) {

    }

    @Override
    public void entriesUpdated(Collection<Jid> addresses) {

    }

    @Override
    public void entriesDeleted(Collection<Jid> addresses) {

    }

    @Override
    public void presenceChanged(Presence presence) {

    }
}
