package com.nairbspace.octoandroid.data.mapper;

import com.nairbspace.octoandroid.data.entity.ConnectEntity;
import com.nairbspace.octoandroid.data.exception.EntityMapperException;
import com.nairbspace.octoandroid.domain.model.Connect;

import rx.Observable;
import rx.Subscriber;

public class ConnectEntityMapper {

    public static Observable.OnSubscribe<ConnectEntity> mapToConnectEntity(final Connect connect) {
        return new Observable.OnSubscribe<ConnectEntity>() {
            @Override
            public void call(Subscriber<? super ConnectEntity> subscriber) {
                String command;
                if (connect.isNotConnected()) {
                    command = ConnectEntity.COMMAND_CONNECT;
                } else {
                    command = ConnectEntity.COMMAND_DISCONNECT;
                }

                String port = connect.ports().get(connect.selectedPortId());
                int baudrate = connect.baudrates().get(connect.selectedBaudrateId());
                int printerProfile = connect.selectedPrinterProfileId();
                String printerProfileId = connect.printerProfiles().get(printerProfile).id();

                try {
                    ConnectEntity connectEntity = ConnectEntity.builder()
                            .command(command)
                            .port(port)
                            .baudrate(baudrate)
                            .printerProfile(printerProfileId)
                            .save(connect.isSaveConnectionChecked())
                            .autoconnect(connect.isAutoConnectChecked())
                            .build();
                    subscriber.onNext(connectEntity);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(new EntityMapperException());
                }
            }
        };
    }
}
