package com.libgdx.cookbook.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.libgdx.cookbook.chp02.SpriteBatchSample;
import com.libgdx.cookbook.chp02.TextureAtlasSample;

/****
 * �оٳ����е�SampleList��Ȼ���ڵ�һ��ҳ��չʾ
 * ���SampleName�ͻ�����Ӧ������
 *
 */
public class SampleList {
	
	@SuppressWarnings("unchecked")
	public static final List<Class< ? extends BaseScreen>> sampleList = new ArrayList<Class< ? extends BaseScreen>>(Arrays.asList(
	 				SpriteBatchSample.class,
	 				TextureAtlasSample.class
	 				));
	
	public List<String> getNames() {
		List<String> names = new ArrayList<String>(sampleList.size());
		// eclipse�����ȥ������:Class is a raw type. References to generic type Class<T> should be parameterized 
		// �������1�����ӱ���ע��@SuppressWarnings("unchecked")
		// �������2��ʹ�÷���ͨ���
		for (Class<?> clazz : sampleList) {
			names.add(clazz.getSimpleName());
		}
		Collections.sort(names);
		return names;
	}
	
	/**
	 *   
	 * @param �����ַ���
	 * @return ���������ַ���������
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
	 * @return ����������ʼ��
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