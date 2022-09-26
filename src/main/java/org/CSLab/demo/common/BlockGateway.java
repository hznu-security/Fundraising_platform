package org.CSLab.demo.common;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlockGateway {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    static public Contract getContract() throws IOException {
        Path walletPath = Paths.get("../","wallet");
        Path networkConfigPath = Paths.get("..","..","first-network","connection-org1.yaml");
        Wallet wallet;
        wallet = Wallet.createFileSystemWallet(walletPath);
        org.hyperledger.fabric.gateway.Gateway.Builder builder = org.hyperledger.fabric.gateway.Gateway.createBuilder();
        builder.identity(wallet,"user1").networkConfig(networkConfigPath).discovery(true);
        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork("mychannel");
        Contract contract = network.getContract("fabcar");
        return contract;
    }



}
