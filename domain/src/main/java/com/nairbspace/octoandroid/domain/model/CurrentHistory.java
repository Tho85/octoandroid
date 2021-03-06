package com.nairbspace.octoandroid.domain.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
@AutoGson(autoValueClass = AutoValue_CurrentHistory.class)
public abstract class CurrentHistory {
    @Nullable
    @SerializedName("state") public abstract State state();
    @Nullable @SerializedName("job") public abstract Job job();
    @Nullable @SerializedName("progress") public abstract Progress progress();
//    @Nullable @SerializedName("currentZ") public abstract Double currentZ();
//    @Nullable @SerializedName("offsets") public abstract Offsets offsets();
    @Nullable @SerializedName("temps") public abstract List<Temps> temps();
    @Nullable @SerializedName("logs") public abstract List<String> logs();
//    @Nullable @SerializedName("messages") public abstract List<String> messages();
//    @Nullable @SerializedName("serverTime") public abstract Double serverTime(); // Pretty sure unix time, not in doc
//    @Nullable @SerializedName("busyFiles") public abstract List<BusyFiles> busyFiles(); // Not in docs

    @AutoValue
    @AutoGson(autoValueClass = AutoValue_CurrentHistory_State.class)
    public abstract static class State {
        @Nullable @SerializedName("text") public abstract String text();
        @Nullable @SerializedName("flags") public abstract Flags flags();

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_State_Flags.class)
        public abstract static class Flags {
            @Nullable @SerializedName("operational") public abstract Boolean operational();
            @Nullable @SerializedName("paused") public abstract Boolean paused();
            @Nullable @SerializedName("printing") public abstract Boolean printing();
            @Nullable @SerializedName("sdReady") public abstract Boolean sdReady();
            @Nullable @SerializedName("error") public abstract Boolean error();
            @Nullable @SerializedName("ready") public abstract Boolean ready();
            @Nullable @SerializedName("closedOrError") public abstract Boolean closedOrError();
        }
    }

    @AutoValue
    @AutoGson(autoValueClass = AutoValue_CurrentHistory_Job.class)
    public abstract static class Job {
        @Nullable @SerializedName("file") public abstract File file();
        @SerializedName("estimatedPrintTime") public abstract double estimatedPrintTime();
        @SerializedName("lastPrintTime") public abstract double lastPrintTime();
//        @Nullable @SerializedName("filament") public abstract Filament filament();

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_Job_File.class)
        public abstract static class File { // Check against this since doesn't match API doc
            @Nullable @SerializedName("origin") public abstract String origin();
            @SerializedName("date") public abstract long date(); // Unix Timestamp (in seconds)
            @Nullable @SerializedName("name") public abstract String name();
            @SerializedName("size") public abstract long size();
        }

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_Job_Filament.class)
        public abstract static class Filament {
            @SerializedName("length") public abstract double length();
            @SerializedName("volume") public abstract double volume();
        }
    }

    @AutoValue
    @AutoGson(autoValueClass = AutoValue_CurrentHistory_Progress.class)
    public abstract static class Progress {
        @SerializedName("completion") public abstract double completion();
        @SerializedName("filepos") public abstract long filepos();
        @SerializedName("printTime") public abstract long printTime();
        @SerializedName("printTimeLeft") public abstract long printTimeLeft();
    }

//    @AutoValue
//    @AutoGson(autoValueClass = AutoValue_CurrentHistory_Offsets.class)
//    public abstract static class Offsets {
//        @Nullable @SerializedName("tool0") public abstract Integer tool0();
//        @Nullable @SerializedName("tool1") public abstract Integer tool1();
//        @Nullable @SerializedName("bed") public abstract Integer bed();
//    }

    @AutoValue
    @AutoGson(autoValueClass = AutoValue_CurrentHistory_Temps.class)
    public abstract static class Temps {
        @SerializedName("time") public abstract long time(); // Unix Timestamp (in seconds)
        @Nullable @SerializedName("tool0") public abstract Tool0 tool0();
        @Nullable @SerializedName("tool1") public abstract Tool1 tool1();
        @Nullable @SerializedName("bed") public abstract Bed bed();

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_Temps_Tool0.class)
        public abstract static class Tool0 {
            @SerializedName("actual") public abstract double actual();
            @SerializedName("target") public abstract double target();
//            @Nullable @SerializedName("offset") public abstract Double offset();
        }

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_Temps_Tool1.class)
        public abstract static class Tool1 {
            @SerializedName("actual") public abstract double actual();
            @SerializedName("target") public abstract double target();
//            @Nullable @SerializedName("offset") public abstract Double offset();
        }

        @AutoValue
        @AutoGson(autoValueClass = AutoValue_CurrentHistory_Temps_Bed.class)
        public abstract static class Bed {
            @SerializedName("actual") public abstract double actual();
            @SerializedName("target") public abstract double target();
//            @Nullable @SerializedName("offset") public abstract Double offset();
        }
    }

//    @AutoValue
//    @AutoGson(autoValueClass = AutoValue_CurrentHistory_BusyFiles.class)
//    public abstract static class BusyFiles {
//        @Nullable @SerializedName("origin") public abstract String origin();
//        @Nullable @SerializedName("name") public abstract String name();
//    }
}