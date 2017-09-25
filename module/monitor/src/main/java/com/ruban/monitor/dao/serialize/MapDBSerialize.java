package com.ruban.monitor.dao.serialize;

import java.io.IOException;

import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.serializer.GroupSerializerObjectArray;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapDBSerialize<T> extends GroupSerializerObjectArray<T> {

    private static final Logger logger = LoggerFactory.getLogger(MapDBSerialize.class);

    private static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

    @Override
    public void serialize(DataOutput2 out, T value) throws IOException {
        byte[] byts = configuration.asByteArray(value);
        out.packInt(byts.length);
        out.write(byts);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(DataInput2 input, int available) throws IOException {

        byte[] byt = new byte[input.unpackInt()];
        input.readFully(byt);
        try {
            return (T) configuration.asObject(byt);
        } catch (Exception ex) {
            logger.error("deserialize error!", ex);
        }

        return null;
    }

}
