package com.passfailerror

class ResultStackValidator {

    static getInstance(){return new ResultStackValidator()}

    boolean stageCallsStep(String stageName, String stepName){
        return resultStackHasStageWithStep(stageName, stepName)
    }

    boolean resultStackHasStageWithStep(String stageName, String stepName){
        def result = ResultStackProcessor.getInstance().getResultStack().getInvocationStack().findAll(item->item.stackLine.contains(stageName))
        if(result.size()>0){
            return resultStackHasStep(result, stepName)
        }
        return false
    }

    boolean resultStackHasStep(List<ResultStack> resultStackList, stepName){
        return resultStackList
                .findAll(resultStackEntry -> resultStackEntry.invocations.containsKey(stepName))
    }

    boolean stageHasEnvVariable(String stageName, String variableName){
        return resultStackHasStageWithEnvVariable(stageName, variableName)
    }

    boolean resultStackHasStageWithEnvVariable(String stageName, String variableName){
        def result = ResultStackProcessor.getInstance().getResultStack().getInvocationStack().findAll(item->item.stackLine.contains(stageName))
        if(result.size()>0) {
            resultStackHasEnvVariable(result, variableName)
        }
        return false
    }

    boolean resultStackHasEnvVariable(List<ResultStack> resultStackList, variableName){
        return resultStackList
                .findAll(resultStackEntry -> resultStackEntry.runtimeVariables.containsKey(variableName))
    }
}
