/*********************************************************************
									FINAL GAME BY										*
																							*
	Ryan Fodor 				Robert Chahanovich					Xiaolu Shi	*
																							*
**********************************************************************/


public class BackGroundMusicPlayer
{
	//Declarations
	SoundClip startScreen = new SoundClip("POL-future-war-short");	
	SoundClip victoryMusic = new SoundClip("POL-tekno-labs-short.wav");
	SoundClip battleMusic = new SoundClip("POL-voyager-short.wav");

	//begin music
	protected void playStartMusic()
	{
		battleMusic.play();
	}
	//play BG music
	protected void playBattleMusic()
	{
		battleMusic.setLooping(true);
		battleMusic.play();
	}
	//Play win music
	protected void playVictoryMusic()
	{
		battleMusic.play();
	}














}