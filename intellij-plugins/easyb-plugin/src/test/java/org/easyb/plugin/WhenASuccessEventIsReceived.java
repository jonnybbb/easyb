package org.easyb.plugin;

import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.easyb.plugin.event.SpecResultEvent;
import org.easyb.plugin.ui.EasybPresenter;
import org.easyb.plugin.ui.EasybView;

public class WhenASuccessEventIsReceived {
    @Test
    public void shouldAddResultToView() {
        EasybView view = createMock(EasybView.class);
        view.addSpecResult("Push onto empty stack");
        expectLastCall();
        replay(view);

        SpecEventListener presenter = new EasybPresenter(view);
        presenter.eventFired(new SpecResultEvent(new SpecResult("Push onto empty stack")));

        verify(view);
    }
}
