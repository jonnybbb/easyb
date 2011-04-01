package org.easyb.listener;

import org.easyb.BehaviorStep;
import org.easyb.result.Result;
import org.easyb.util.BehaviorStepType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Centralizes the report data preparation in one place.
 */
public class ResultsReporter {
    private static final List<BehaviorStepType> EXAMINE_FURTHER = Arrays.asList(BehaviorStepType.WHERE,
            BehaviorStepType.EXECUTE, BehaviorStepType.GENESIS);
    private static final List<BehaviorStepType> LEGAL_BUT_WEIRD = Arrays.asList(BehaviorStepType.WHEN,
            BehaviorStepType.GIVEN, BehaviorStepType.THEN);
    private static final List<BehaviorStepType> IGNORE = Arrays.asList(BehaviorStepType.DESCRIPTION,
            BehaviorStepType.NARRATIVE, BehaviorStepType.NARRATIVE_ROLE, BehaviorStepType.NARRATIVE_FEATURE,
            BehaviorStepType.NARRATIVE_BENEFIT, BehaviorStepType.EXTENSION_POINT);

    private BehaviorStep genesisStep;
    private List<StepReporter> stories = new ArrayList<StepReporter>();
    private List<StepReporter> specifications = new ArrayList<StepReporter>();

    public ResultsReporter(BehaviorStep genesisStep) {
        this.genesisStep = genesisStep;
        collectSteps(genesisStep);
    }

    private void collectSteps(BehaviorStep step) {
        if (step.getStepType() == BehaviorStepType.STORY) {
            stories.add(StepReporter.asStory(step));
        } else if (step.getStepType() == BehaviorStepType.SPECIFICATION) {
            specifications.add(StepReporter.asSpecification(step));
        } else if (step.getStepType() == BehaviorStepType.GENESIS) {
            for (BehaviorStep child : step.getChildSteps()) {
                collectSteps(child);
            }
        } else if (!IGNORE.contains(step.getStepType())) {
            throw new RuntimeException("Unknown step " + step);
        }
    }

    public StepReporter findStory(BehaviorStep step) {
        for (StepReporter sc : stories) {
            if (sc.step == step)
                return sc;
        }
        throw new RuntimeException("Request for non-existent story " + step);
    }

    public StepReporter findSpecification(BehaviorStep step) {
        for (StepReporter sc : specifications) {
            if (sc.step == step)
                return sc;
        }

        throw new RuntimeException("Request for non-existent specification" + step);
    }


    public long getStoryBehaviorCount() {
        return stories.size();
    }

    public long getSpecificationBehaviorCount() {
        return specifications.size();
    }

    public List<StepReporter> getSpecifications() {
        return specifications;
    }

    public List<StepReporter> getStories() {
        return stories;
    }

    public List<StepReporter> getFailedStories() {
        List<StepReporter> failed = new ArrayList<StepReporter>();
        for (StepReporter story : stories) {
            if (story.getFailedScenarioCount() > 0) {
                failed.add(story);
            }
        }
        return failed;
    }

    public List<StepReporter> getFailedSpecifications() {
        List<StepReporter> failed = new ArrayList<StepReporter>();
        for (StepReporter spec : specifications) {
            if (spec.getFailedSpecificationCount() > 0) {
                failed.add(spec);
            }
        }
        return failed;
    }

    public long getFailedSpecificationCount() {
        return getSpecificationCount(Result.FAILED);
    }

    public long getFailedScenarioCount() {
        return getScenarioCount(Result.FAILED);
    }

    public long getIgnoredScenarioCount() {
        return getScenarioCount(Result.IGNORED);
    }

    public long getSuccessBehaviorCount() {
        return getScenarioCount(Result.SUCCEEDED) + getSpecificationCount(Result.SUCCEEDED);
    }

    public long getSuccessScenarioCount() {
        return getScenarioCount(Result.SUCCEEDED);
    }

    public long getSuccessSpecificationCount() {
        return getSpecificationCount(Result.SUCCEEDED);
    }

    public long getPendingSpecificationCount() {
        return getSpecificationCount(Result.PENDING);
    }

    public long getPendingScenarioCount() {
        return getScenarioCount(Result.PENDING);
    }

    private long getSpecificationCount(Result.Type status) {
        long count = 0;
        for (StepReporter spec : specifications) {
            count += spec.getCount(status);
        }
        return count;
    }

    private long getScenarioCount(Result.Type status) {
        long count = 0;
        for (StepReporter story : stories) {
            count += story.getCount(status);
        }
        return count;
    }

    public long getSpecificationCount() {
        long count = 0;
        for (StepReporter spec : specifications) {
            count += spec.scenarios.size();
        }
        return count;
    }

    public long getScenarioCount() {
        long count = 0;
        for (StepReporter story : stories) {
            count += story.scenarios.size();
        }
        return count;
    }

    public long getBehaviorCount() {
        return getScenarioCount() + getSpecificationCount();
    }

    public long getFailedBehaviorCount() {
        return getFailedScenarioCount() + getFailedSpecificationCount();
    }

    public long getPendingBehaviorCount() {
        return getPendingScenarioCount() + getPendingSpecificationCount();
    }

    public long getTotalBeforeCount(Result.Type type) {
        long count = 0;
        for (StepReporter spec : specifications) {
            count += spec.getBeforesCount(type);
        }
        for (StepReporter story : stories) {
            count += story.getBeforesCount(type);
        }
        return count;
    }

    public long getTotalBeforeEachCount(Result.Type type) {
        long count = 0;
        for (StepReporter story : stories) {
            count += story.getBeforeEachCount(type);
        }
        return count;
    }

    public long getTotalAfterCount(Result.Type type) {
        long count = 0;
        for (StepReporter spec : specifications) {
            count += spec.getAftersCount(type);
        }
        for (StepReporter story : stories) {
            count += story.getAftersCount(type);
        }
        return count;
    }

    public long getTotalAfterEachCount(Result.Type type) {
        long count = 0;
        for (StepReporter story : stories) {
            count += story.getAfterEachCount(type);
        }
        return count;
    }

    public BehaviorStep getGenesisStep() {
        return genesisStep;
    }

    public String getSpecificationResultsAsText() {
        return (getSpecificationCount() == 1 ? "1 specification" : getSpecificationCount() + " specifications") +
                (getPendingSpecificationCount() > 0 ? " (including " + getPendingSpecificationCount() + " pending)" :
                        "") + " executed" + (getFailedSpecificationCount() > 0 ?
                ", but status is failure! Total failures: " + getFailedSpecificationCount() : " successfully.");
    }

    public String getScenarioResultsAsText() {
        long scenariosExecuted = getScenarioCount() - getIgnoredScenarioCount();
        return (scenariosExecuted == 1 ? "1 scenario" :
                scenariosExecuted + (getIgnoredScenarioCount() > 0 ? " of " + getScenarioCount() : "") + " scenarios") +
                (getPendingScenarioCount() > 0 ? " (including " + getPendingScenarioCount() + " pending)" : "") +
                " executed" +
                (getFailedScenarioCount() > 0 ? ", but status is failure! Total failures: " + getFailedScenarioCount() :
                        " successfully.");
    }

    public static class StepReporter {
        private BehaviorStep step;
        private List<BehaviorStep> befores = new ArrayList<BehaviorStep>();
        private List<BehaviorStep> scenarios = new ArrayList<BehaviorStep>();
        private List<BehaviorStep> afters = new ArrayList<BehaviorStep>();

        /* holder for random given/then/when in a story which is legal syntax but weird */
        private BehaviorStep fakeScenario = null;

        private StepReporter(BehaviorStep step) {
            this.step = step;
        }

        public int getScenarioCount() {
            return scenarios.size();
        }

        public int getFailedScenarioCount() {
            return getCount(scenarios, Result.FAILED);
        }

        public int getPendingScenarioCount() {
            return getCount(scenarios, Result.PENDING);
        }

        public int getSpecificationCount() {
            return scenarios.size();
        }

        public int getFailedSpecificationCount() {
            return getCount(scenarios, Result.FAILED);
        }

        public int getPendingSpecificationCount() {
            return getCount(scenarios, Result.PENDING);
        }

        public int getBeforesCount(Result.Type status) {
            return getCount(befores, status);
        }

        public int getBeforeEachCount(Result.Type status) {
            return getCount(scenarios, BehaviorStepType.BEFORE_EACH, status);
        }

        public int getAfterEachCount(Result.Type status) {
            return getCount(scenarios, BehaviorStepType.AFTER_EACH, status);
        }

        public int getAftersCount(Result.Type status) {
            return getCount(afters, status);
        }

        public int getCount(Result.Type resultType) {
            return getCount(scenarios, resultType);
        }

        private int getCount(List<BehaviorStep> steps, Result.Type resultType) {
            return getCount(steps, null, resultType);
        }

        private int getCount(List<BehaviorStep> steps, BehaviorStepType stepType) {
            return getCount(steps, stepType, null);
        }

        private int getCount(List<BehaviorStep> steps, BehaviorStepType stepType, Result.Type resultType) {
            int count = 0;
            for (BehaviorStep step : steps) {
                if (stepType == null || stepType == step.getStepType()) {
                    if (resultType == null || (step.getResult() != null && resultType == step.getResult().status())) {
                        count++;
                    }
                }
            }
            return count;
        }

        public static StepReporter asStory(final BehaviorStep step) {
            return collectStory(new StepReporter(step), step);
        }

        private static StepReporter collectStory(final StepReporter reporter, final BehaviorStep step) {
            for (BehaviorStep childStep : step.getChildSteps()) {
                if (EXAMINE_FURTHER.contains(childStep.getStepType())) {
                    collectStory(reporter, childStep);
                } else if (childStep.getStepType() == BehaviorStepType.SCENARIO) {
                    reporter.scenarios.add(childStep);
                    fixScenarioStatus(childStep);
                } else if (childStep.getStepType() == BehaviorStepType.BEFORE) {
                    reporter.befores.add(childStep);
                    fixScenarioStatus(childStep);
                } else if (childStep.getStepType() == BehaviorStepType.AFTER) {
                    reporter.afters.add(childStep);
                    fixScenarioStatus(childStep);
                } else if (LEGAL_BUT_WEIRD.contains(childStep.getStepType())) {
                    if (reporter.fakeScenario == null) {
                        reporter.fakeScenario = new BehaviorStep(BehaviorStepType.SCENARIO, "fake");
                        reporter.scenarios.add(reporter.fakeScenario);
                    }
                    reporter.fakeScenario.addChildStep(childStep);
                    fixScenarioStatus(reporter.fakeScenario);
                } else if (!IGNORE.contains(childStep.getStepType())) {
                    throw new RuntimeException("Unknown step " + childStep);
                }
            }
            return reporter;
        }

        public static void fixScenarioStatus(BehaviorStep step) {
            // if its ignored, don't care about whats inside it
            if (step.getResult() != null && step.getResult().status() == Result.IGNORED) {
                return;
            }

            // failed trumps pending
            if (fixScenarioStatus(step, Result.FAILED) > 0) {
                step.setResult(new Result(Result.FAILED));
            } else if (fixScenarioStatus(step, Result.PENDING) > 0) {
                step.setResult(new Result(Result.PENDING));
            } else if (step.getResult() == null) {
                step.setResult(new Result(Result.SUCCEEDED));
            }
        }

        private static int fixScenarioStatus(BehaviorStep step, Result.Type status) {
            int count = 0;
            for (BehaviorStep child : step.getChildSteps()) {
                if (child.getStepType() == BehaviorStepType.BEFORE_EACH ||
                        child.getStepType() == BehaviorStepType.AFTER_EACH) {
                    int subCount = fixScenarioStatus(child, status);

                    // set the correct status while we are at it
                    if (subCount > 0)
                        child.setResult(new Result(status));

                    count += subCount;
                } else if (child.getResult() != null && child.getResult().status() == status) {
                    count++;
                }
            }
            return count;
        }

        public static StepReporter asSpecification(final BehaviorStep step) {
            return collectSpecification(new StepReporter(step), step);
        }

        private static StepReporter collectSpecification(final StepReporter reporter, final BehaviorStep step) {
            for (BehaviorStep childStep : step.getChildSteps()) {
                if (EXAMINE_FURTHER.contains(childStep.getStepType())) {
                    collectSpecification(reporter, childStep);
                } else if (childStep.getStepType() == BehaviorStepType.IT) {
                    reporter.scenarios.add(childStep);
                } else if (childStep.getStepType() == BehaviorStepType.BEFORE) {
                    reporter.befores.add(childStep);
                } else if (childStep.getStepType() == BehaviorStepType.AFTER) {
                    reporter.afters.add(childStep);
                } else if (!IGNORE.contains(childStep.getStepType())) {
                    throw new RuntimeException("Unknown step " + childStep);
                }
            }
            return reporter;
        }
    }
}
