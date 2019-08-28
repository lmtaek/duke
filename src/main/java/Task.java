public abstract class Task {

    enum TaskType {
        TODO, DEADLINE, EVENT;
    }

    private String taskName;
    private Boolean isDone = false;
    private String time;

    public TaskType taskType;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

//    public Enum getTaskType() {
//        return taskType;
//    }

    public String getTime() {
        return time;
    }

    public Boolean isTaskDone() {
        return isDone;
    }

    public void markAsDone() {
        if (this.isDone != true) {
            this.isDone = true;
        }
    }
}

