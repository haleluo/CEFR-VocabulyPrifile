package com.example.myapplication

import android.content.res.AssetManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.persistence.WordExamples
import com.example.myapplication.persistence.WordsDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    /* */
    private lateinit var mViewPager2: ViewPager2
    private lateinit var dialogPage: DialogPage
    private lateinit var dialogSearch: DialogSearch
    private lateinit var speakerLeft: FloatingActionButton
    private lateinit var speakerRight: FloatingActionButton
    private lateinit var assetManager: AssetManager

    private lateinit var words: Array<WordExamples>

    private val mediaPlayer = MediaPlayer()

    private var totalPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        assetManager = assets

        mViewPager2 = findViewById(R.id.viewpager2);

        val wordDao = WordsDatabase.getInstance(this).wordDao()
        var levelList = mutableListOf<Int>()

        // dialog
        dialogPage = DialogPage(this, object : DialogPage.clickCallBack {
            override fun yesClick(dialog: DialogPage) {
                if (dialog.current > 0) {
                    mViewPager2.setCurrentItem(dialog.current - 1, true)
                }
                dialog.dismiss()
            }
        })
//        myDialog.setOnDismissListener {
//            // 关闭监听逻辑
//            Toast.makeText(this, "on dismiss", Toast.LENGTH_SHORT).show()
//        }
        dialogSearch = DialogSearch(this, object : DialogSearch.clickCallBack {
            override fun yesClick(dialog: DialogSearch) {
                var word = dialog.editText?.text
                if (!word.isNullOrEmpty()) {
                    words = wordDao.querySubWords("$word%")
                    if (words.isNotEmpty()) {
                        val prefs = getSharedPreferences("vocabulary", 0)
                        val editor = prefs.edit()

                        // current level
                        when (prefs.getInt("level", 0)) {
                            10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                            20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                            21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                            22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                            31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                            32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                        }

                        totalPage = words.size
                        mViewPager2.adapter =
                            WordViewAdapter(words, object : WordViewAdapter.callBack {
                                override fun updateAudio(fileName: String) {
                                }
                            })

                        editor.putInt("level", 0).apply()
                    }
                }
                dialog.dismiss()
            }
        })


        val prefs = getSharedPreferences("vocabulary", 0)
        var currentPage: Int = 0
        when (prefs.getInt("level", 10)) {
            10 -> {
                levelList.add(11)
                levelList.add(12)
                currentPage = prefs.getInt("levela_page", 0)
            }
            20 -> {
                levelList.add(21)
                levelList.add(22)
                currentPage = prefs.getInt("levelb_page", 0)
            }
            21 -> {
                levelList.add(21)
                currentPage = prefs.getInt("levelb1_page", 0)
            }
            22 -> {
                levelList.add(22)
                currentPage = prefs.getInt("levelb2_page", 0)
            }
            31 -> {
                levelList.add(31)
                currentPage = prefs.getInt("levelc1_page", 0)
            }
            32 -> {
                levelList.add(32)
                currentPage = prefs.getInt("levelc2_page", 0)
            }
        }

        val words = wordDao.getSubWords(levelList)
        totalPage = words.size

        mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
            override fun updateAudio(fileName: String) {

            }
        })
        mViewPager2.setCurrentItem(currentPage, false)

        speakerLeft = findViewById(R.id.audio_left)
        speakerLeft.setOnClickListener {
            initMediaPlayer()
            mediaPlayer.start()
        }
        speakerRight = findViewById(R.id.audio_right)
        speakerRight.setOnClickListener {
            initMediaPlayer()
            mediaPlayer.start()
        }
    }

    override fun onStop() {
        super.onStop()

        val prefs = getSharedPreferences("vocabulary", 0)
        val editor = prefs.edit()

        var currentLevel = prefs.getInt("level", 0)
        when (currentLevel) {
            10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
            20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
            21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
            22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
            31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
            32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val wordDao = WordsDatabase.getInstance(this).wordDao()
        val prefs = getSharedPreferences("vocabulary", 0)
        val editor = prefs.edit()
        when (item.itemId) {
            R.id.menu_setting -> {
                //显示的位置居中提高100dp-------------
                val w = dialogPage.window
                val lp = w!!.attributes
                lp.x = 0
                lp.y = -100
                //-----------------------------------

//                myDialog.show();
                dialogPage.showDialog(totalPage, mViewPager2.currentItem + 1);

                //显示的宽高根据屏幕百分比调整-----------------------------------
                val m = this!!.windowManager
                val d = m.defaultDisplay //为获取屏幕宽、高
                val p = dialogPage.window!!.attributes //获取对话框当前的参数值
//                p.height = (d.height * 0.24).toInt() //高度设置为屏幕的0.3
                p.width = (d.width * 0.9).toInt() //宽度设置为屏幕的0.5
                dialogPage.window!!.attributes = p
                //--------------------------------------------------------------
            }

            R.id.menu_search -> {
                val w = dialogPage.window
                val lp = w!!.attributes
                lp.x = 0
                lp.y = -100

                dialogSearch.show()
            }

            R.id.menu_levela -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 10) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(11, 12))
                totalPage = words.size
                Toast.makeText(this, "Level A1 & A2: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levela_page", 0), false)
                editor.putInt("level", 10).apply()
            }
            R.id.menu_levelb -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 20) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(21, 22))
                totalPage = words.size
                Toast.makeText(this, "Level B1 & B2: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levelb_page", 0), false)
                editor.putInt("level", 20).apply()
            }
            R.id.menu_levelb1 -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 21) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(21))
                totalPage = words.size
                Toast.makeText(this, "Level B1: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levelb1_page", 0), false)
                editor.putInt("level", 21).apply()
            }
            R.id.menu_levelb2 -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 22) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(22))
                totalPage = words.size
                Toast.makeText(this, "Level B2: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levelb2_page", 0), false)
                editor.putInt("level", 22).apply()
            }
            R.id.menu_levelc1 -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 31) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(31))
                totalPage = words.size
                Toast.makeText(this, "Level C1: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levelc1_page", 0), false)
                editor.putInt("level", 31).apply()
            }
            R.id.menu_levelc2 -> {
                // current level
                var currentLevel = prefs.getInt("level", 0)
                if (currentLevel == 32) {
                    return true
                }
                when (currentLevel) {
                    10 -> editor.putInt("levela_page", mViewPager2.currentItem).apply()
                    20 -> editor.putInt("levelb_page", mViewPager2.currentItem).apply()
                    21 -> editor.putInt("levelb1_page", mViewPager2.currentItem).apply()
                    22 -> editor.putInt("levelb2_page", mViewPager2.currentItem).apply()
                    31 -> editor.putInt("levelc1_page", mViewPager2.currentItem).apply()
                    32 -> editor.putInt("levelc2_page", mViewPager2.currentItem).apply()
                }

                words = wordDao.getSubWords(listOf(32))
                totalPage = words.size
                Toast.makeText(this, "Level C2: " + words.size.toString(), Toast.LENGTH_SHORT)
                    .show()
                mViewPager2.adapter = WordViewAdapter(words, object : WordViewAdapter.callBack {
                    override fun updateAudio(fileName: String) {
                    }
                })
                mViewPager2.setCurrentItem(prefs.getInt("levelc2_page", 0), false)
                editor.putInt("level", 32).apply()
            }
        }
        return true
    }

    private fun initMediaPlayer() {
        mediaPlayer.reset()
        try {
            words[mViewPager2.currentItem].voc.audio?.let {
                val fd = assetManager.openFd(it)
                mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                mediaPlayer.prepare()
            }

        } catch (e: Exception) { }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}
