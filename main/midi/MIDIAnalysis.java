package main.midi;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MIDIAnalysis {
	private byte[] b = new byte[100000];
	private int totalByte;
	private int format; //MIDIフォーマット
	private int totalTracks; //合計トラック数
	private int timeUnit; //時間単位
	private int currentTrack = -1; //現在のトラック番号
	private double tempo; //BPM
	private int index;

	ArrayList<ArrayList<MIDIMessage>> totalList;

	public static void main(String[] arg) {
		MIDIAnalysis midiAnalysis = new MIDIAnalysis();
		midiAnalysis.analysis("Practice.mid");
	}

	public int conversion() {
		String bin = Integer.toBinaryString(Byte.toUnsignedInt(b[index]));
		//先頭に0を詰めて8文字にする
		while(bin.length() != 8) {
			bin = "0" + bin;
		}
		//先頭ビットが1なら次のアドレスを見る
		while(b[index] < 0) {
			index++;
			String nextBin = Integer.toBinaryString(Byte.toUnsignedInt(b[index]));
			//先頭に0を詰めて8文字にする
			while(nextBin.length() != 8) {
				nextBin = "0" + nextBin;
			}
			bin = bin + nextBin;
		}
		//可変長判定用のビットを削除
		int len = bin.length() / 8;
		for(int cnt = len - 1; cnt >= 0; cnt--) {
			if(cnt == 0) {
				bin = bin.substring(1);
			} else {
				bin = bin.substring(0, cnt * 8) + bin.substring(cnt * 8 + 1);
			}
		}
		return Integer.parseInt(bin, 2);
	}

	/*	MIDI解析	*/
	public void analysis(String filename) {
		totalList = new ArrayList<ArrayList<MIDIMessage>>();

		try {
			//ファイルの読み込み
			DataInputStream input =
				new DataInputStream(
					new BufferedInputStream(
						new FileInputStream(filename)));
			System.out.println("ファイルを読み込みました。");

			if ((totalByte = input.read(b)) != -1) {
				totalList.add(new ArrayList<MIDIMessage>()); //0フレーム目のMIDIメッセージ配列を生成

				System.out.println("totalByte is " + totalByte);
				//MIDIファイルであるかどうか判定
				if(totalByte > 14
				&& b[0] == 0x4D
				&& b[1] == 0x54
				&& b[2] == 0x68
				&& b[3] == 0x64) {
					//フォーマット、トラック数、時間単位を検出
					format = Byte.toUnsignedInt(b[8]) * 256 + Byte.toUnsignedInt(b[9]);
					totalTracks = Byte.toUnsignedInt(b[10]) * 256 + Byte.toUnsignedInt(b[11]);
					timeUnit = Byte.toUnsignedInt(b[12]) * 256 + Byte.toUnsignedInt(b[13]);
					System.out.println("フォーマット" + format);
					System.out.println("トラック数：" + totalTracks);
					System.out.println("時間単位：" + timeUnit);


					for(index = 13; index < totalByte; index++) {
						int preEvent = 0; //直前の命令を保持
						int count = 0; //曲中における現在位置をフレーム単位で保持
						int time = 0; //曲中における現在位置をデルタタイムの時間単位で保持

						//System.out.println(b[i]);
						//トラックデータの始点を検出
						if(b[index] == 0x4D
						&& b[index + 1] == 0x54
						&& b[index + 2] == 0x72
						&& b[index + 3] == 0x6B) {
							//トラックナンバー、データ長を検出
							currentTrack++;
							if(currentTrack >= totalTracks) {break;}
							long dataLength =Byte.toUnsignedInt(b[index + 5]) * 256 * 256 +
											 Byte.toUnsignedInt(b[index + 6]) * 256 +
											 Byte.toUnsignedInt(b[index + 7]);
							int dataLengthBuffer = Byte.toUnsignedInt(b[index + 4]); //数値が大きくなりすぎる場合の措置（未実装）
							System.out.println("トラックナンバー：" + currentTrack);
							System.out.println(" デーア長：" + dataLength);
							index += 7;
							//**	トラックデータ解析	**//
							long limit = dataLength + index;
							while(index < limit) {
								index++;
								//**	デルタタイム解析	**//
								int bin = this.conversion();
//								System.out.println("デルタタイム：" + bin);
								index++;
								if(currentTrack >= 1) {
									time += bin; //曲中における位置をデルタタイム分進める
									count = (int)((double)time / (1000/30 * timeUnit / (tempo/1000))); //64分単位に変換
									//必要な分だけMIDIメッセージのリストを拡張
									while(totalList.size() <= count + 1) {
										totalList.add(new ArrayList<MIDIMessage>());
									}
								}
								//**	イベント解析	**//
								int event = Byte.toUnsignedInt(b[index]); //このインデックスで実行するイベント
								//ランニングステータス
								if(event < 0x80) {
									event = preEvent;
									System.out.println("ランニングステータス");
								} else {
									preEvent = Byte.toUnsignedInt(b[index]);
									index++;
								}
								//メタイベントの実行
								if(event == 0xFF) {
									int eventNum = Byte.toUnsignedInt(b[index]);
									index++;
									int metaLength = this.conversion();
									byte[] meta = new byte[metaLength];
									switch(eventNum) {
									//トラック名
									case 0x03:
										for(int cnt = 0; cnt < metaLength; cnt++) {
											index++;
											meta[cnt] = b[index];
										}
										System.out.println("トラック名：" + new String(meta));
										break;
									//テンポ変更
									case 0x51:
										index += metaLength;
										tempo =
												Byte.toUnsignedInt(b[index-2]) * 256 * 256 +
												Byte.toUnsignedInt(b[index-1]) * 256 +
												Byte.toUnsignedInt(b[index]);
										System.out.println("テンポ変更：" + tempo);
										break;
									//トラック終端
									case 0x2F:
										System.out.println("トラック" + currentTrack + "終了");
										break;
									//不明なメタイベント
									default:
										System.out.println("メタイベント0x" + Integer.toHexString(eventNum) + "を実行");
										index += metaLength;
									}
								}
								//ノートオン
								else if(event >= 0x90
								&& event <= 0x9F) {
									int ch = event - 0x90 + 1;
									int key = Byte.toUnsignedInt(b[index]);
									index++;
									int vel = Byte.toUnsignedInt(b[index]);
									totalList.get(count).add(new MIDIMessage(currentTrack, key, 0));
									System.out.println("ノートON　チャンネル：" + ch + "　音階：" + key + "　ベロシティ：" + vel);
								}
								//ノートオフ
								else if(event >= 0x80
								&& event <= 0x8F) {
									int ch = event - 0x80 + 1;
									int key = Byte.toUnsignedInt(b[index]);
									index++;
									int vel = Byte.toUnsignedInt(b[index]);
									System.out.println("ノートOFF　チャンネル：" + ch + "　音階：" + key + "　ベロシティ：" + vel);
								}
								System.out.println("現在フレーム:" + count);
							}
						}
						System.out.println("最終フレーム:" + totalList.size());
					}

				} else {
					System.out.println("MIDIファイルを指定してください。");
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("ファイルが見つかりません。");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<MIDIMessage>> getList() {
		return totalList;
	}
}
