# Ethereum_SCVADetection
In 2020, my colleague Mélissa Mazrou and I worked together to develop a tool named SCVADetection (Smart Contract Vulnerability and Attack Detection) for Ethereum Smart Contracts.

The tool is written in Java and Python, and used via a GUI which is so simple. 

**The vulnerabilies and attacks detected by our tool are :**
- **App1** : Based on a Machine Learning approach, it detects Reentrancy Attack, Integer Overflow and Underflow, Transaction order dependence, Timestamp dependence.
- **App2** : Reentrancy Attack.
- **App3** : Reentrancy Vulnerability.
- **App4** : Incorrect Blockhash, Timestamp dependence, Blocknumber dependence, Pseudo-Random Number Generator (PRNG)

To edit the source code I suggest you, using Eclipse IDE. You need also to create an **Infura account** https://app.infura.io/  to get an **APIKey** (cf, source code where I mention "PUT_HERE_YOUR_API_KEY") which will be used to connect to **Web3**.

Finaly, you would like to test with these smart contracts :

**App1 :**
- **Vulnerable contract** : 

	  0xe3f640efb74229b6e6c9e3fe24ded660f683cdd2
    
- **Non vulnerable contract** : 

      0xa0c6bb9567821ddfb2597f4c660bc084cd1386f9

**App2 :**

- **Vulnerable/(Already attacked) contract :**

      0xbb9bc244d798123fde783fcc1c72d3bb8c189413 aka TheDAO you can test the following range of analysis block : Between 1718497 and 1718597 

      0xf91546835f756da0c10cfa0cda95b15577b84aa7 aka SpankChain

      0x0eb68f34efa0086e4136bca51fc4d0696580643e

      0x23a91059fdc9579a9fbd0edc5f2ea0bfdb70deb4

      0x26b8af052895080148dabbc1007b3045f023916e

      0x95d34980095380851902ccd9a1fb4c813c2cb639

      0x463f235748bc7862deaa04d85b4b16ac8fafef39

      0x59752433dbe28f5aa59b479958689d353b3dee08

      0x903643251af408a3c5269c836b9a2a4a1f04d1cf

      0xa4e1cbf64c3b5db2a6e6f23cb5286b97d80b86e3

      0xa5d6accc5695327f65cbf38da29198df53efdcf0

      0xaae1f51cf3339f18b6d3f3bdc75a5facd744b0b8

      0xb4c05e6e4cdb07c15095300d96a5735046eef999

      0xb7c5c5aa4d42967efe906e1b66cb8df9cebf04f7

      0xb93430ce38ac4a6bb47fb1fc085ea669353fd89e

      0xbabfe0ae175b847543724c386700065137d30e3b

      0xbf78025535c98f4c605fbe9eaf672999abf19dc1

      0xcb6fe98097fe7d6e00415bb6623d5fc3effa4e83

      0xcead721ef5b11f1a7b530171aab69b16c5e66b6e

      0xd116d1349c1382b0b302086a4e4219ae4f8634ff

      0xd654bdd32fc99471455e86c2e7f7d7b6437e9179

      0xdf4b83a451ef20b925ce39f4da2a021722688370

      0xe610af01f92f19679327715b426c35849c47c657

      0xf01fe1a15673a5209c94121c45e2121fe2903416

      0xfe1b613f17f984e27239b0b2dccfb1778888dfae

- **Non vulnerable contract :** 

      0x0d8775f648430679a709e98d2b0cb6250d2887ef

      0x0f017e732efb3dd784c3908e024a884c7ccc9685

      0x01cb539331a8a9d768256177f4df05ee1202aee9

      0x2a549b4af9ec39b03142da6dc32221fc390b5533

      0x4bdde1e9fbaef2579dd63e2abbf0be445ab93f10

      0x5acc84a3e955bdd76467d3348077d003f00ffb97

      0x7a250d5630b4cf539739df2c5dacb4c659f2488d

      0x04203b7668eb832a5cbfe248b57defeb709e48e3

      0x7722891ee45ad38ae05bda8349ba4cf23cfd270f

      0x9047237b16c918d94db3d1a9a86d7a2a605cdfe7

      0xa2acfaaaa01c4999385104787c1f24a9cf95b725

      0xa153c1bd407ff2467bb221412ae34b93107b1476

      0xb0c4382d4355cdfe94a132fadf92a509b1e25939

      0xb077242e4c62dd3acc935fc5acdc288474de6f58

      0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2

      0xc83e009c7794e8f6d1954dc13c23a35fc4d039f6

      0xce825efe338f79efb8bddb00a8321c7fab60c32b

      0xef07beb03e66533b4862f62a280995bb84cbaf13

      0x72f60eca0db6811274215694129661151f97982e

      0xc6b330df38d6ef288c953f1f2835723531073ce2

      0xd4cd7c881f5ceece4917d856ce73f510d7d0769e

      0xe752b7d837a6969a5986467b0109bdf052e45bdb

      0x52a03d5d2bb8a30585dfc59904c799b27a03a56e

      0xe870d00176b2c71afd4c43cea550228e22be4abd


**App3 :**

- **Vulnerable contract :**

		0xbb9bc244d798123fde783fcc1c72d3bb8c189413 aka TheDAO
		
		0xf91546835f756da0c10cfa0cda95b15577b84aa7 aka SpankChain
		
		0xaae1f51cf3339f18b6d3f3bdc75a5facd744b0b8 aka DEP_BANK
		
		0x4bdde1e9fbaef2579dd63e2abbf0be445ab93f10 aka CityCoin (CITY)
			
		0x0eb68f34efa0086e4136bca51fc4d0696580643e
		
		0x4a8d3a662e0fd6a8bd39ed0f91e4c1b729c81a38
				
		0x7a250d5630b4cf539739df2c5dacb4c659f2488d
		
		0x23a91059fdc9579a9fbd0edc5f2ea0bfdb70deb4
		
		0x95d34980095380851902ccd9a1fb4c813c2cb639
				
		0x463f235748bc7862deaa04d85b4b16ac8fafef39
		
		0x903643251af408a3c5269c836b9a2a4a1f04d1cf
		
		0xa4e1cbf64c3b5db2a6e6f23cb5286b97d80b86e3
		
		0xa5d6accc5695327f65cbf38da29198df53efdcf0
				
		0xb4c05e6e4cdb07c15095300d96a5735046eef999
		
		0xb7c5c5aa4d42967efe906e1b66cb8df9cebf04f7
		
		0xb93430ce38ac4a6bb47fb1fc085ea669353fd89e
    
		0xbabfe0ae175b847543724c386700065137d30e3b
		
		0xcb6fe98097fe7d6e00415bb6623d5fc3effa4e83
			
		0xcead721ef5b11f1a7b530171aab69b16c5e66b6e
			
		0xd116d1349c1382b0b302086a4e4219ae4f8634ff
			
		0xdf4b83a451ef20b925ce39f4da2a021722688370
			
		0xe610af01f92f19679327715b426c35849c47c657
			
		0xef07beb03e66533b4862f62a280995bb84cbaf13
			
      0xfe1b613f17f984e27239b0b2dccfb1778888dfae
    
      0x2a549b4af9ec39b03142da6dc32221fc390b5533
		
      0x26b8af052895080148dabbc1007b3045f023916e
		
      0x59abb8006b30d7357869760d21b4965475198d9d
		
      0x59752433dbe28f5aa59b479958689d353b3dee08
		
      0xbf78025535c98f4c605fbe9eaf672999abf19dc1
		
      0xd654bdd32fc99471455e86c2e7f7d7b6437e9179
		
      0xf01fe1a15673a5209c94121c45e2121fe2903416

- **Non vulnerable contract :** 

  		0x0d8775f648430679a709e98d2b0cb6250d2887ef
    
  		0x0f017e732efb3dd784c3908e024a884c7ccc9685
		
      0x01cb539331a8a9d768256177f4df05ee1202aee9
		
      0x5acc84a3e955bdd76467d3348077d003f00ffb97
		
      0x04203b7668eb832a5cbfe248b57defeb709e48e3
		
      0x7722891ee45ad38ae05bda8349ba4cf23cfd270f

      0x9047237b16c918d94db3d1a9a86d7a2a605cdfe7

      0xa2acfaaaa01c4999385104787c1f24a9cf95b725

      0xa153c1bd407ff2467bb221412ae34b93107b1476

      0xb0c4382d4355cdfe94a132fadf92a509b1e25939

      0xb077242e4c62dd3acc935fc5acdc288474de6f58

      0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2

      0xc6b330df38d6ef288c953f1f2835723531073ce2

      0xc83e009c7794e8f6d1954dc13c23a35fc4d039f6

      0xce825efe338f79efb8bddb00a8321c7fab60c32b

      0x72f60eca0db6811274215694129661151f97982e

      0xd4cd7c881f5ceece4917d856ce73f510d7d0769e

      0xe870d00176b2c71afd4c43cea550228e22be4abd

      0x52a03d5d2bb8a30585dfc59904c799b27a03a56e

      0xe752b7d837a6969a5986467b0109bdf052e45bdb

      0x72f60eca0db6811274215694129661151f97982e

      0xd4cd7c881f5ceece4917d856ce73f510d7d0769e

      0xe870d00176b2c71afd4c43cea550228e22be4abd

      0x52a03d5d2bb8a30585dfc59904c799b27a03a56e

      0xe752b7d837a6969a5986467b0109bdf052e45bdb


**App4 :**

- **Vulnerable contract :**

		0x5ace17f87c7391e5792a7683069a8025b83bbd85 aka SmartBillions
		
		0x80ddae5251047d6ceb29765f38fed1c0013004b7 aka Lottery
		
		0xa11e4ed59dc94e69612f3111942626ed513cb172 aka EtherLotto
		
		0xa65d59708838581520511d98fb8b5d1f76a96cad aka BlackJack
		
		0xba6284ca128d72b25f1353fadd06aa145d9095af aka EthStick
		
		0xcac337492149bdb66b088bf5914bedfbf78ccc18 aka TheRun
		
		0xcc88937f325d1c6b97da0afdbb4ca542efa70870 aka Ethraffle v4b
		
		0xf767fca8e65d03fe16d4e38810f5e5376c3372a8 aka LuckyDoubler
		
		0xf45717552f12ef7cb65e95476f217ea008167ae3 aka GovernMental

      0x0d8775f648430679a709e98d2b0cb6250d2887ef aka BAT (BAT)

      0x0f017e732efb3dd784c3908e024a884c7ccc9685 aka HiCash (HICA)

      0xa153c1bd407ff2467bb221412ae34b93107b1476 aka UniversalGoldExchange (UGDX)

      0x7722891ee45ad38ae05bda8349ba4cf23cfd270f 

      0xc83e009c7794e8f6d1954dc13c23a35fc4d039f6 

      0xce825efe338f79efb8bddb00a8321c7fab60c32b  

- **Non vulnerable contract :**

      0x01cb539331a8a9d768256177f4df05ee1202aee9
      
	    0x5acc84a3e955bdd76467d3348077d003f00ffb97
	    
      0x7a250d5630b4cf539739df2c5dacb4c659f2488d
	    
      0x04203b7668eb832a5cbfe248b57defeb709e48e3
	    
      0x9047237b16c918d94db3d1a9a86d7a2a605cdfe7
	    
      0xa2acfaaaa01c4999385104787c1f24a9cf95b725
	    
      0xb0c4382d4355cdfe94a132fadf92a509b1e25939
	    
      0xb077242e4c62dd3acc935fc5acdc288474de6f58
	    
      0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2
	    
      0xe870d00176b2c71afd4c43cea550228e22be4abd
      
      0x52a03d5d2bb8a30585dfc59904c799b27a03a56e

