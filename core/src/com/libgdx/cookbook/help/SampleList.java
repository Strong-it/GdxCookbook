package com.libgdx.cookbook.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.libgdx.cookbook.chp02.AnimatedSpriteSample;
import com.libgdx.cookbook.chp02.OrthographicCameraSample;
import com.libgdx.cookbook.chp02.SpriteBatchSample;
import com.libgdx.cookbook.chp02.SpriteBatchTest1;
import com.libgdx.cookbook.chp02.SpriteBatchTest2;
import com.libgdx.cookbook.chp02.SpriteSample;
import com.libgdx.cookbook.chp02.TextureAtlasSample;
import com.libgdx.cookbook.chp02.TextureRegionSample;
import com.libgdx.cookbook.chp02.ViewportSample;
import com.libgdx.cookbook.chp03.FrameBufferParticleEffectSample;
import com.libgdx.cookbook.chp03.FrameBufferSample;
import com.libgdx.cookbook.chp03.ParticleEffectsSample;
import com.libgdx.cookbook.chp04.GestureDetectorSample;
import com.libgdx.cookbook.chp04.InputListeningSample;
import com.libgdx.cookbook.chp04.InputMappingSample;
import com.libgdx.cookbook.chp04.InputMultiplexerSample;
import com.libgdx.cookbook.chp04.InputPollingSample;
import com.libgdx.cookbook.chp05.CarEngineSample;
import com.libgdx.cookbook.chp05.FileHandlingSample;
import com.libgdx.cookbook.chp05.JsonParsingSample;
import com.libgdx.cookbook.chp05.MusicSample;
import com.libgdx.cookbook.chp05.PreferencesSample;
import com.libgdx.cookbook.chp05.SoundEffectSample;
import com.libgdx.cookbook.chp05.SpatialAudioSample;
import com.libgdx.cookbook.chp05.XmlParsingSample;
import com.libgdx.cookbook.chp06.BitmapFontSample;
import com.libgdx.cookbook.chp06.DistanceFieldEffectsSample;
import com.libgdx.cookbook.chp06.DistanceFieldFontSample;
import com.libgdx.cookbook.chp06.HieroFontEffectsSample;
import com.libgdx.cookbook.chp07.AssetManagerSample;
import com.libgdx.cookbook.chp07.CustomLoaderSample;
import com.libgdx.cookbook.chp07.GroupingAssetsSample;
import com.libgdx.cookbook.chp07.ProgressBarSample;
import com.libgdx.cookbook.chp08.ActorSample;
import com.libgdx.cookbook.chp08.CustomWidgetSample;
import com.libgdx.cookbook.chp08.GroupSample;
import com.libgdx.cookbook.chp08.LabelScaleSample;
import com.libgdx.cookbook.chp08.MainMenuSample;
import com.libgdx.cookbook.chp08.PixmapSample;
import com.libgdx.cookbook.chp08.SkinCustomizationSample;
import com.libgdx.cookbook.chp08.StageSample;
import com.libgdx.cookbook.chp08.WidgetsSample;
import com.libgdx.cookbook.chp09.TiledMapObjectsSample;
import com.libgdx.cookbook.chp09.TiledMapSample;
import com.libgdx.cookbook.chp10.Box2DComplexShapesSample;
import com.libgdx.cookbook.chp10.Box2DSimpleSample;
import com.libgdx.cookbook.chp10.Box2dTest;
import com.libgdx.cookbook.chp11.ArtificialIntelligenceSample;
import com.libgdx.cookbook.chp11.LocalizationSample;

/****
 * 列举出所有的SampleList，然后在第一个页面展示
 * 点击SampleName就会进入对应的例子
 *
 */
public class SampleList {
	
	@SuppressWarnings("unchecked")
	public static final List<Class< ? extends BaseScreen>> sampleList = new ArrayList<Class< ? extends BaseScreen>>(Arrays.asList(
	 				SpriteBatchSample.class,
	 				SpriteBatchTest1.class,
	 				SpriteBatchTest2.class,
	 				TextureRegionSample.class,
	 				TextureAtlasSample.class,
	 				ViewportSample.class,
	 				SpriteSample.class,
	 				AnimatedSpriteSample.class,
	 				ParticleEffectsSample.class,
	 				FrameBufferSample.class,
	 				FrameBufferParticleEffectSample.class,
	 				InputPollingSample.class,
	 				OrthographicCameraSample.class,
	 				InputListeningSample.class,
	 				InputMultiplexerSample.class,
	 				GestureDetectorSample.class,
	 				InputMappingSample.class,
	 				SoundEffectSample.class,
	 				MusicSample.class,
	 				CarEngineSample.class,
	 				SpatialAudioSample.class,
	 				FileHandlingSample.class,
	 				PreferencesSample.class,
	 				XmlParsingSample.class,
	 				JsonParsingSample.class,
	 				BitmapFontSample.class,
	 				HieroFontEffectsSample.class,
	 				DistanceFieldFontSample.class,
	 				DistanceFieldEffectsSample.class,
	 				AssetManagerSample.class,
	 				ProgressBarSample.class,
	 				CustomLoaderSample.class,
	 				GroupingAssetsSample.class,
	 				ActorSample.class,
	 				LabelScaleSample.class,
	 				StageSample.class,
	 				GroupSample.class,
	 				PixmapSample.class,
	 				WidgetsSample.class,
	 				MainMenuSample.class,
	 				SkinCustomizationSample.class,
	 				CustomWidgetSample.class,
	 				TiledMapSample.class,
	 				TiledMapObjectsSample.class,
	 				LocalizationSample.class,
	 				ArtificialIntelligenceSample.class,
	 				Box2DSimpleSample.class,
	 				Box2DComplexShapesSample.class,
	 				Box2dTest.class
	 				));
	
	public List<String> getNames() {
		List<String> names = new ArrayList<String>(sampleList.size());
		// eclipse中如何去除警告:Class is a raw type. References to generic type Class<T> should be parameterized 
		// 解决方法1：增加编译注解@SuppressWarnings("unchecked")
		// 解决方法2：使用泛型通配符
		for (Class<?> clazz : sampleList) {
			names.add(clazz.getSimpleName());
		}
		Collections.sort(names);
		return names;
	}
	
	/**
	 *   
	 * @param 类名字符串
	 * @return 根据类名字符串返回类
	 */
	private static Class<? extends BaseScreen> forName(String name) {
		for (Class<? extends BaseScreen> clazz : sampleList) {
				if (clazz.getSimpleName().equals(name)) {
					return clazz;
				}
		}
		return null;
	}
	
	/**
	 * 
	 * @param sampleName  
	 * @return 返回类对象初始化
	 */
	public static BaseScreen newSample(String sampleName) {
		try {
			return ClassReflection.newInstance(forName(sampleName));
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
