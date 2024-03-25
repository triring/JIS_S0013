# Javaで作成した「JIS S 0013:2002 高齢者・障害者配慮設計指針　―　消費生活製品の報知音」生成プログラム 


## 解説  

Arduino用にJIS S 0013に準拠した報知音を生成するライブラリ[AudSig](https://github.com/triring/AudSig)を作成し、公開したところ、この報知音を直接聞けるようにして欲しいという要望があった。  
以前、報知音のサウンドファイルを生成するプログラムを作った記憶があったので、探してみたところ、Javaで作成したコードを見つけた。  
2002年年頃に、Windowsのコマンドプロンプト環境で開発し、その後、何度か手をいれ、そのまま放置した状態だった。  
今回、ソースリストに最小限の手を加え、Windows上のWSL(linux)環境で実行できるようにし、ドキュメントに加筆訂正したものをここに公開する。  

* ライセンスをGPL2から"THE BEER-WARE LICENSE"に変更
* ソースコード、ドキュメントをSJISからUTF-8に変更。
* 上記の変更に伴いコンパイルに -encoding UTF-8 オプションを追加
* 非推奨になったコードを修正

開発した当時の規格は、JIS S 0013:2002 だったので、それに準拠して作成したが、現在、この規格は、JIS S 0013:2022 に改定されている。しかし、大きな差異はないと思うので、そのまま公開する。  
問題がある場合は、公開しているソースコードを修正して対応していただきたい。  

## このプログラムについて

JIS S 0013:2002  
高齢者・障害者配慮設計指針－消費生活製品の報知音  

　上記規格にて推奨されている報知音を、8KHzの.au形式音声ファイルとして生成するためのJavaプログラムである。

使ってもらいたい方々  

* 報知音を必要とする機器を開発されるエンジニアの方
* 音によるインターフェースをデザインされる方


## パターンファイルの作成

パターンファイルは，以下のような構造となっている。
    $ cat pattern/End_Far7.ptn
    # End_Far7.ptn       終了音　遠 7  パターンを5回繰り返す
    5 100 100 500 500


1. 行の先頭に'#'のある行は，コメントとして扱われる。  
2. 1番最初のパラメータは，繰り返し回数の指定する。例では，5回の繰り返しを指定している。  
3. 繰り返し回数に続けて，信号のON時間，信号のOFF時間をミリ秒単位でスペースを空けて書く。  
例では，発振100ms,休止100ms,発振500ms,休止500msを指定している。  
pattern ディレクトリに，サンプルのパターンファイルを準備してあるので、これらを参考にすること。  

### 報知音パターンファイルのリスト

pattern/
- BasePoint1.ptn     基点音 1      対象音の前に受付音が2回鳴る
- BasePoint2.ptn     基点音 2      対象音の前に受付音が2回鳴る

- Start1.ptn         受付・スタート音 1 単純音1回
- Start2.ptn         受付・スタート音 2 単純音1回

- Stop1.ptn          停止音 1      対象音の前に受付音が1回鳴る
- Stop2.ptn          停止音 2      対象音の前に受付音が1回鳴る

- End_Far1.ptn       終了音　遠 1  パターンを5回繰り返す
- End_Far2.ptn       終了音　遠 2  パターンを5回繰り返す
- End_Far3.ptn       終了音　遠 3  パターンを5回繰り返す
- End_Far4.ptn       終了音　遠 4  パターンを5回繰り返す
- End_Far5.ptn       終了音　遠 5  組み合わせ音1回
- End_Far6.ptn       終了音　遠 6  組み合わせ音1回
- End_Far7.ptn       終了音　遠 7  パターンを5回繰り返す

- End_Near1.ptn      終了音　近 1  単純音1回
- End_Near2.ptn      終了音　近 2  単純音1回
- End_Near3.ptn      終了音　近 3  単純音1回
- End_Near4.ptn      終了音　近 4  組み合わせ音1回

- CautionNeeded1.ptn 注意音　強 1 単純繰り返し(回数は任意)今回は5秒間の繰り返し
- CautionNeeded2.ptn 注意音　強 2 単純繰り返し(回数は任意)今回は5秒間の繰り返し
- CautionNeeded3.ptn 注意音　強 3 単純繰り返し(回数は任意)今回は5秒間の繰り返し
- CautionNeeded4.ptn 注意音　強 4 単純繰り返し(回数は任意)今回は約5秒間の繰り返し

- Attention1.ptn     注意音　弱 1 単純繰り返し(回数は任意)今回は5秒間の繰り返し
- Attention2.ptn     注意音　弱 2 単純繰り返し(回数は任意)今回は5秒間の繰り返し
- Attention3.ptn     注意音　弱 3 組み合わせ音繰り返し(回数は任意)今回は5秒間の繰り返し

- SOS.ptn            国際救難信号,救難信号,モールス信号では「・・・, - - -, ・・・」、「・」は短音、「-」は長音

## 報知音の生成

JIS.class, JIS$s0013.classがプログラム本体。JIS.class, JIS$s0013.classを保存しているディレクトリに移動し，コマンドラインから以下の書式で実行する。  

    java JIS$s0013 <patternfile> <aufile>

    patternfile : 読み込むパターンファイル名
    aufile      : 出力するauファイル名，拡張子は，必ず，.auにすること。

以下に実行例を示す。

    $ java JIS\$s0013 End_Far7.ptn End_Far7.au
    # End_Far7.ptn       終了音　遠 7  パターンを5回繰り返す
    Loop    : 5
    Pattern : 100 100 500 500
    0        1         2         3         4         5         6         7         8         9 (sec)
    ----+----|----+----|----+----|----+----|----+----|----+----|----+----|----+----|----+----|
    W-WWWWW-----W-WWWWW-----W-WWWWW-----W-WWWWW-----W-WWWWW-----


'W'の部分は発振期間を示している。
'-'の部分は休止期間を示している。

主要な報知音パターンで生成した .auファイルを auディレクトリに保管した。
また、.auファイルを wav形式に変換したファイルを wavディレクトリに保管した。
Javaの実行環境がない場合は、以下よりダウンロードすることができる。

[au/](https://github.com/triring/JIS_S0013/tree/master/au)  
[wav/](https://github.com/triring/JIS_S0013/tree/master/wav)  


## 報知音の試聴  

wav形式のファイルで出力した報知音をダウンロードできるようにしました。
ブラウザによっては、そのまま
再生ソフトを呼び出し、すぐに視聴することが可能です。

### 基点音  

[基点音 1](https://github.com/triring/JIS_S0013/raw/master/wav/BasePoint1.wav)	対象音の前に受付音が2回鳴る  
[基点音 2](https://github.com/triring/JIS_S0013/raw/master/wav/BasePoint2.wav)	対象音の前に受付音が2回鳴る  

### 受付・スタート音  

[受付・スタート音 1](https://github.com/triring/JIS_S0013/raw/master/wav/Start1.wav)	単純音1回  
[受付・スタート音 2](https://github.com/triring/JIS_S0013/raw/master/wav/Start2.wav)	単純音1回  

### 停止音  

[停止音 1](https://github.com/triring/JIS_S0013/raw/master/wav/Stop1.wav)	対象音の前に受付音が1回鳴る  
[停止音 2](https://github.com/triring/JIS_S0013/raw/master/wav/Stop2.wav)	対象音の前に受付音が1回鳴る  

### 終了音　遠  

[終了音　遠 1](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far1.wav)	パターンを5回繰り返す  
[終了音　遠 2](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far2.wav)	パターンを5回繰り返す  
[終了音　遠 3](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far3.wav)	パターンを5回繰り返す  
[終了音　遠 4](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far4.wav)	パターンを5回繰り返す  
[終了音　遠 5](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far5.wav)	組み合わせ音1回  
[終了音　遠 6](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far6.wav)	組み合わせ音1回  
[終了音　遠 7](https://github.com/triring/JIS_S0013/raw/master/wav/End_Far7.wav)	パターンを5回繰り返す  

### 終了音　近  

[終了音　近 1](https://github.com/triring/JIS_S0013/raw/master/wav/End_Near1.wav)	単純音1回  
[終了音　近 2](https://github.com/triring/JIS_S0013/raw/master/wav/End_Near2.wav)	単純音1回  
[終了音　近 3](https://github.com/triring/JIS_S0013/raw/master/wav/End_Near3.wav)	単純音1回  
[終了音　近 4](https://github.com/triring/JIS_S0013/raw/master/wav/End_Near4.wav)	組み合わせ音1回  

### 注意音　強  

[注意音　強 1](https://github.com/triring/JIS_S0013/raw/master/wav/CautionNeeded1.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  
[注意音　強 2](https://github.com/triring/JIS_S0013/raw/master/wav/CautionNeeded2.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  
[注意音　強 3](https://github.com/triring/JIS_S0013/raw/master/wav/CautionNeeded3.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  
[注意音　強 4](https://github.com/triring/JIS_S0013/raw/master/wav/CautionNeeded4.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  

### 注意音　弱  

[注意音　弱 1](https://github.com/triring/JIS_S0013/raw/master/wav/Attention1.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  
[注意音　弱 2](https://github.com/triring/JIS_S0013/raw/master/wav/Attention2.wav)	単純繰り返し（回数は任意）今回は5秒間の繰り返し  
[注意音　弱 3](https://github.com/triring/JIS_S0013/raw/master/wav/Attention3.wav)	組み合わせ音繰り返し（回数は任意）今回は5秒間の繰り返し  

### おまけ  

[国際救難信号 SOS](https://github.com/triring/JIS_S0013/raw/master/wav/SOS.wav)	モールス信号では「・・・, - - -, ・・・」、「・」は短音、「-」は長音  

<!-- pandoc -f markdown -t html5 -o readme.html -c github.css readme.md -->

## コンパイル

内部の処理に興味がある場合は，ソースリスト JIS.java を参照すること。
コンパイル方法は以下の通り。
特殊なクラスは使用していないが，内部クラスを使用しているので、JDK1.1以上の開発環境が必要。
現在使用されているOS環境から考えるとJava 2 SDK, Standard Edition 1.3.0以上の開発環境を推奨する。
以下のようにしてコンパイルする。

    $ javac -encoding UTF-8 JIS.java

## ライセンス

お粗末なコードではあるが、[Poul-Henning Kamp](https://people.freebsd.org/%7Ephk/) 氏が提唱しているBEER-WAREライセンスを踏襲し配布する。  

### "THE BEER-WARE LICENSE" (Revision 42):
<akio@triring.net> wrote this file. As long as you retain this notice you
can do whatever you want with this stuff. If we meet some day, and you think this stuff is worth it, you can buy me a beer in return.
Copyright (c) 2024 Akio MIWA @triring  

### "THE BEER-WARE LICENSE" (第42版):
このファイルは、<akio@triring.net> が書きました。あなたがこの条文を載せている限り、あなたはソフトウェアをどのようにでも扱うことができます。
もし、いつか私達が出会った時、あなたがこのソフトに価値があると感じたなら、見返りとして私にビールを奢ることができます。  
Copyright (c) 2024 Akio MIWA @triring  

## 謝辞ならびに参考文献

　このプログラムは、財団法人　家電製品協会の家電製品の操作性向上のための調査、研究の成果である下記の資料を元に作成しました。  
　これらの貴重な資料を提供してくださった財団法人　家電製品協会に深く感謝いたします。  

1. 高齢者・障害者にも使いやすい家電製品開発指針:平成11年3月,財団法人　家電製品協会
2. 「報知音モニター調査」報告書:平成12年度,財団法人　家電製品協会 
3. 家電製品における操作性向上のための報知音に関するガイドライン:平成13年7月,財団法人　家電製品協会
4. 報知音推奨音CD-ROM,2001年,財団法人　家電製品協会
5. JIS S 0013:2002　高齢者・障害者配慮設計指針－消費生活製品の報知音,平成14年1月20日制定,日本規格協会

## 関連リンク

* 財団法人 家電製品協会	http://www.aeha.or.jp/ehframe.htm
* 財団法人 日本規格協会	http://www.jsa.or.jp/
* JIS S 0013:2002 高齢者・障害者配慮設計指針　―　消費生活製品の報知音
　http://www.jsa.or.jp/catalog/jis_srch_dtl.asp?fn=d007260.html

規格の詳細については、上記の規格書を入手してお読みください。
