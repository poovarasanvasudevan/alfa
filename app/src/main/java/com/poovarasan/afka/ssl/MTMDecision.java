package com.poovarasan.afka.ssl;

/**
 * Created by poovarasanv on 18/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 18/5/17 at 10:08 AM
 */

public class MTMDecision {
    public final static int DECISION_INVALID	= 0;
    public final static int DECISION_ABORT		= 1;
    public final static int DECISION_ONCE		= 2;
    public final static int DECISION_ALWAYS	= 3;

    int state = DECISION_INVALID;
}
