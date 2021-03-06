package org.hildan.livedoc.core.test.flow;

import org.hildan.livedoc.core.annotations.flow.ApiFlow;
import org.hildan.livedoc.core.annotations.flow.ApiFlowSet;
import org.hildan.livedoc.core.annotations.flow.ApiFlowStep;

@ApiFlowSet
public class TestFlow1 {

    @ApiFlow(name = "Flow1", description = "Description for flow 1",
            steps = {@ApiFlowStep(apimethodid = "M1"), @ApiFlowStep(apimethodid = "M2"),
                    @ApiFlowStep(apimethodid = "M3")})
    public void flow1() {

    }

    @ApiFlow(name = "Flow2", description = "Description for flow 2",
            steps = {@ApiFlowStep(apimethodid = "M0"), @ApiFlowStep(apimethodid = "M4"),
                    @ApiFlowStep(apimethodid = "M5")})
    public void flow2() {

    }

}
