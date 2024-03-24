#!/bin/sh
mkdir au
java JIS\$s0013 pattern/Attention1.ptn      au/Attention1.au
java JIS\$s0013 pattern/Attention2.ptn      au/Attention2.au
java JIS\$s0013 pattern/Attention3.ptn      au/Attention3.au
java JIS\$s0013 pattern/BasePoint1.ptn      au/BasePoint1.au
java JIS\$s0013 pattern/BasePoint2.ptn      au/BasePoint2.au
java JIS\$s0013 pattern/CautionNeeded1.ptn  au/CautionNeeded1.au
java JIS\$s0013 pattern/CautionNeeded2.ptn  au/CautionNeeded2.au
java JIS\$s0013 pattern/CautionNeeded3.ptn  au/CautionNeeded3.au
java JIS\$s0013 pattern/CautionNeeded4.ptn  au/CautionNeeded4.au
java JIS\$s0013 pattern/End_Far1.ptn        au/End_Far1.au
java JIS\$s0013 pattern/End_Far2.ptn        au/End_Far2.au
java JIS\$s0013 pattern/End_Far3.ptn        au/End_Far3.au
java JIS\$s0013 pattern/End_Far4.ptn        au/End_Far4.au
java JIS\$s0013 pattern/End_Far5.ptn        au/End_Far5.au
java JIS\$s0013 pattern/End_Far6.ptn        au/End_Far6.au
java JIS\$s0013 pattern/End_Far7.ptn        au/End_Far7.au
java JIS\$s0013 pattern/End_Near1.ptn       au/End_Near1.au
java JIS\$s0013 pattern/End_Near2.ptn       au/End_Near2.au
java JIS\$s0013 pattern/End_Near3.ptn       au/End_Near3.au
java JIS\$s0013 pattern/End_Near4.ptn       au/End_Near4.au
java JIS\$s0013 pattern/Start1.ptn          au/Start1.au
java JIS\$s0013 pattern/Start2.ptn          au/Start2.au
java JIS\$s0013 pattern/Stop1.ptn           au/Stop1.au
java JIS\$s0013 pattern/Stop2.ptn           au/Stop2.au
java JIS\$s0013 pattern/SOS.ptn             au/SOS.au

mkdir wav
sox au/Attention1.au     wav/Attention1.wav
sox au/Attention2.au     wav/Attention2.wav
sox au/Attention3.au     wav/Attention3.wav
sox au/BasePoint1.au     wav/BasePoint1.wav
sox au/BasePoint2.au     wav/BasePoint2.wav
sox au/CautionNeeded1.au wav/CautionNeeded1.wav
sox au/CautionNeeded2.au wav/CautionNeeded2.wav
sox au/CautionNeeded3.au wav/CautionNeeded3.wav
sox au/CautionNeeded4.au wav/CautionNeeded4.wav
sox au/End_Far1.au       wav/End_Far1.wav
sox au/End_Far2.au       wav/End_Far2.wav
sox au/End_Far3.au       wav/End_Far3.wav
sox au/End_Far4.au       wav/End_Far4.wav
sox au/End_Far5.au       wav/End_Far5.wav
sox au/End_Far6.au       wav/End_Far6.wav
sox au/End_Far7.au       wav/End_Far7.wav
sox au/End_Near1.au      wav/End_Near1.wav
sox au/End_Near2.au      wav/End_Near2.wav
sox au/End_Near3.au      wav/End_Near3.wav
sox au/End_Near4.au      wav/End_Near4.wav
sox au/Start1.au         wav/Start1.wav
sox au/Start2.au         wav/Start2.wav
sox au/Stop1.au          wav/Stop1.wav
sox au/Stop2.au          wav/Stop2.wav
sox au/SOS.au            wav/SOS.wav

