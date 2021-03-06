package com.nairbspace.octoandroid.data.entity;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.nairbspace.octoandroid.domain.model.AutoGson;

@AutoValue
@AutoGson(autoValueClass = AutoValue_EventEntity.class)
public abstract class EventEntity {
    @Nullable @SerializedName("type") public abstract String type();
    @Nullable @SerializedName("payload") public abstract Object payload(); // No doc on payload so spit out as object
}
