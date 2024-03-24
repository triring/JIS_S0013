/***********************************************************
JIS.class
JIS$s0013.class
Copyright (C) 2002 - 2023, @trirng
e-mail : akio@okakogi.go.jp

"THE BEER-WARE LICENSE" (Revision 42):
akio@triring.net wrote this file.
As long as you retain this notice you can do whatever you want with this stuff.
If we meet some day, and you think this stuff is worth it, you can buy me a beer in return.
Akio MIWA @triring

オリジナル作成日:2002年 5月17日 金曜日
JIS.java -- JIS S 0013 報知音推奨音に準拠した2KHzのau形式音声ファイルの出力する

修正日:2023年 3月23日 日曜日
ライセンスをGPL2から"THE BEER-WARE LICENSE"に変更
ソースコード、ドキュメントをSJISからUTF-8に変更。これに伴いコンパイルに -encoding UTF-8 を追加
非推奨になったコードを修正
	修正前:value = new Integer(num.trim());
	修正後:value = Integer.valueOf(num.trim());
コンパイル
$ javac -encoding UTF-8 JIS.java
実行例
$ java  JIS$s0013 patternfile aufile
***********************************************************/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.System;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

public class JIS {
   byte[]  wavedata;    // 波形データの保存

   /* Executor.java:31: 警告: [unchecked] raw 型 java.util.Vector の
      メンバとしての addElement(E) への無検査呼び出しです。
      v.addElement((Integer)cmd);
      この問題を解決するには、以下のように総称型という機能を使って、
      Vectorに格納する形をあらかじめ指定しておく必要がある。*/
   java.util.Vector<Integer> PlayPattern;

   // コンストラクタ
   // パターンファイルを受け取り音声データを生成する。
   JIS(String PatternFile) {
      /* 再生データを保存管理するオブジェクトの生成 */
      PlayPattern = new java.util.Vector<Integer>();

      // すべての要素を削除する。Vector は空になる。 
      PlayPattern.removeAllElements();
      try {
         /**  File 名に該当する File を開く    */
         /**  単純にFileStreamを使うよりも、   */
         /**  Bufferedのクラスを介在させる方が */
         /**  入出力が早くなる                 */
         FileInputStream   fis  = new FileInputStream(PatternFile);
         InputStreamReader isr  = new InputStreamReader(fis);
         BufferedReader    fin  = new BufferedReader(isr);

         StringTokenizer st;
         String lb;
         String num;
         Integer value = null;
         try {
            while ((lb = fin.readLine()) != null) {
               if ('#' == lb.charAt(0)) {
                  System.out.println(lb);
                  continue; // コメントの場合は読み飛ばす。
               }
               st = new StringTokenizer(lb, " ,\t\n");
               try {
                  while ((num = st.nextToken()) != null) {
                     value = null;
                     try {
                     // value = new Integer(num.trim()); 非推奨になっていたので、以下のように書き換える。(2024/03/22)
                        value = Integer.valueOf(num.trim());
                     } catch (NumberFormatException nfe) {
                        break;
                        // String が解析可能な整数型を含まない場合
                     }
                     if (null != value) {
                        PlayPattern.addElement(value);
                     }
                  }
               } catch (NoSuchElementException nee) {}
            }
         } catch (IOException ie2) {}
         /////////////////////////////////////////////////////////////////
         // ファイルを閉じ、オブジェクトを開放する。
         fin.close();
         fin  = null;
         isr  = null;
         fis  = null;
      } catch (FileNotFoundException e) {
         /**  File 自体が存在しない場合 */
         System.out.print("File not found.  ------ " + PatternFile + "\n");
         System.exit(0);
      } catch (IOException e) {
         /**  File はあっても開けない場合 (ディレクトリとか)*/
         System.out.print("Cannot access file. --- " + PatternFile + "\n");
         System.exit(0);
      }
      // パターンの読み込み終了
   }

   // 読み込んだパターンファイルからloop回数の読み出しを行う。
   public int getLoop() {
      int loop = 0;  // loop回数
      loop = ((Integer)PlayPattern.elementAt( 0 )).intValue();
      if (0 == loop) loop = 1;
      System.out.print("Loop    : " + loop + "\n");
      return loop;
   }

   // 読み込んだパターンファイルからpatternデータ長を計算を行う。
   public int getPatternLength() {
      int sum  = 0;  // データ長
      Integer IntVal;
      System.out.print("Pattern : ");
      for (int i = 1; i < PlayPattern.size(); i++) {
         IntVal = (Integer)PlayPattern.elementAt( i );
         sum += IntVal.intValue();
         System.out.print(IntVal.intValue() + " ");
      }
      System.out.print("\n");
      return sum;
   }

   // 音声波形の合成
   public int WaveSynthesis() {
      int count   = 0;
      int loop    = getLoop();
      int pattern = getPatternLength();
System.out.println(
"0        1         2         3         4         5         6         7         8         9 (sec)");
System.out.println(
"----+----|----+----|----+----|----+----|----+----|----+----|----+----|----+----|----+----|");
      Integer IntVal;
      // 8000Hzでサンプリングした時のdata配列の大きさ
      wavedata = new byte[pattern * 8 * loop];
      for (int l = 0; l < loop; l++) {  // loop回数
         for (int j = 1; j < PlayPattern.size(); j++) {
            IntVal = (Integer)PlayPattern.elementAt( j );
            int ms = IntVal.intValue();
            int i, c;
            if (0 != (j % 2)) { // 奇数の時は音の再生
               // 8000Hz / 2000Hz = 4   1波長分のdata長
               // 1msは、8byte分
               for (i = 0; i < ms; i++) {
                  wavedata[count    ] = (byte)0xff;
                  wavedata[count + 1] = (byte)0x86;
                  wavedata[count + 2] = (byte)0xff;
                  wavedata[count + 3] = (byte)0x06;
                  wavedata[count + 4] = (byte)0xff;
                  wavedata[count + 5] = (byte)0x86;
                  wavedata[count + 6] = (byte)0xff;
                  wavedata[count + 7] = (byte)0x06;
                  count += 8;
               }
               c = (int)(ms / 100);
               for (i = 0; i < c; i++) System.out.print("W");
            } else {            // 偶数の時は音を休止
               for (i = 0; i < ms; i++) {
                  wavedata[count    ] = (byte)0xff;
                  wavedata[count + 1] = (byte)0xff;
                  wavedata[count + 2] = (byte)0xff;
                  wavedata[count + 3] = (byte)0xff;
                  wavedata[count + 4] = (byte)0xff;
                  wavedata[count + 5] = (byte)0xff;
                  wavedata[count + 6] = (byte)0xff;
                  wavedata[count + 7] = (byte)0xff;
                  count += 8;
               }
               c = (int)(ms / 100);
               for (i = 0; i < c; i++) System.out.print("-");
            }
         }
      }
      System.out.print("\n");
      return (pattern * 8 * loop);
   }

   // 音声ファイルの保存
   public void save(String AuFile, int size) {
      // ヘッダ部データ作成用byte配列
      byte byte4ID[]       = new byte[4];
      byte byte4Offset[]   = new byte[4];
      byte byte4Length[]   = new byte[4];
      byte byte4Encoding[] = new byte[4];
      byte byte4Sampling[] = new byte[4];
      byte byte4Channels[] = new byte[4];
      byte byte4Info1[]    = new byte[4];
      byte byte4Info2[]    = new byte[4];
      byte byte4Info3[]    = new byte[4];
      byte byte4Info4[]    = new byte[4];
      byte byte4Info5[]    = new byte[4];

      byte4ID       = makeID('.','s','n','d'); // 4 char   ID='.snd'
      byte4Offset   = DWORDtoByte4(  44); // 1 dword  Offset of start of sample
      byte4Length   = DWORDtoByte4(size); // 1 dword  Length of stored sample
      byte4Encoding = DWORDtoByte4(   1); // 1 dword  Sound encoding :
                                          //  1 -  8-bit ISDN u-law
                                          //  2 -  8-bit linear PCM (REF-PCM)
                                          //  3 - 16-bit linear PCM
                                          //  4 - 24-bit linear PCM
                                          //  5 - 32-bit linear PCM
                                          //  6 - 32-bit IEEE floating point
                                          //  7 - 64-bit IEEE floating point
                                          // 23 -  8-bit ISDN u-law compressed(G.721 ADPCM)
      byte4Sampling = DWORDtoByte4(8000); // 1 dword  Sampling rate
      byte4Channels = DWORDtoByte4(   1); // 1 dword  Number of sample channels

      byte4Info1    = makeID('J','I','S','_'); // 4 char   ID='JIS_'
      byte4Info2    = makeID('S','_','0','0'); // 4 char   ID='S_00'
      byte4Info3    = makeID('1','3',':','2'); // 4 char   ID='13:2'
      byte4Info4    = makeID('0','0','2','_'); // 4 char   ID='002_'
      byte4Info5    = makeID('0','1','2','0'); // 4 char   ID='0120'
 
      try {
         /**  File 名に該当する File を開く */
         FileOutputStream    fout;
         fout = new FileOutputStream(AuFile);
         fout.write(byte4ID      );
         fout.write(byte4Offset  );
         fout.write(byte4Length  );
         fout.write(byte4Encoding);
         fout.write(byte4Sampling);
         fout.write(byte4Channels);
         fout.write(byte4Info1   );
         fout.write(byte4Info2   );
         fout.write(byte4Info3   );
         fout.write(byte4Info4   );
         fout.write(byte4Info5   );
         for (int i = 0; i < wavedata.length; i++) {
            fout.write((byte)wavedata[i]);
         }
         fout.close();
      } catch (IOException ie) {
         System.out.println(ie);
         System.out.println("\""+ AuFile + "\"" + " 書き込むためにファイルをオープンできません.");
      } catch (SecurityException  se) {
         System.out.println(se);
         System.out.println("\""+ AuFile + "\"" + " セキュリティ上の問題があるので、書き込むためにファイルをオープンできません.");
      }
      System.exit(0);
   }

   /**
    * 4つのcharの値を変換して 4個の要素を持つByte配列に納める。
    */
   public byte[] makeID(char id0, char id1, char id2, char id3) {
      byte  id[] = new byte[4];    /* ID               FOURCC  4 */
      id[0] = (byte)id0;
      id[1] = (byte)id1;
      id[2] = (byte)id2;
      id[3] = (byte)id3;
      return id;
   }

   /**
    * dWordの値を変換して 4個の要素を持つByte配列に納める。
    */
   public byte[] DWORDtoByte4( int dword ) {
      int  mask   = 0x000000ff;
      byte array[] = new byte[4];
      array[0] = (byte)((dword >> 24) & mask);  // little endian
      array[1] = (byte)((dword >> 16) & mask);
      array[2] = (byte)((dword >>  8) & mask);
      array[3] = (byte)( dword        & mask);
      return array;
   }

   public static class s0013 {
      public static void main(String args[]) {
         String  PatternFile = new String();
         String  AuFile      = new String();
         /*
          * Java, unlike C/C++ does not need argument count (argc)
          */
         switch (args.length) {
            case  2 : PatternFile = args[0];
                   AuFile      = args[1];
                   break;
            case  0 : System.out.println("JIS$s0013.class");
                      System.out.println("Copyright (C) 2002 - 2024, Akio MIWA");
                      System.out.println("Published under the GNU GPL");
                      System.out.println("https://www.okakogi.go.jp/People/miwa/");
                      System.out.println("e-mail : akio@triring.net\n\n");

            default : System.out.println("usage: java JIS$s0013 patternfile aufile");
                   System.out.println("Example>java JIS\\$s0013 start.ptn start.au");
                   System.exit (1);
         }
         // パターンファイルを読み込みコンストラクタを生成
         JIS jis  = new JIS(PatternFile);

         // 音声波形の合成
         int size = jis.WaveSynthesis();

         // 音声ファイルの保存
         jis.save(AuFile, size);

      }
   }
}
