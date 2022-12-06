package top.zsmile.test.entity;

@FunctionalInterface
public interface IfElseFunction {

    void trueOrFalseHandle(Runnable trueRun, Runnable falseRun);

    static IfElseFunction isTrueOrFalse(boolean bool) {

        return (tr, fr) -> {
            if (bool) {
                tr.run();
            } else {
                fr.run();
            }
        };
    }
}
