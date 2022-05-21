package com.mygdx.wave;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture[] textures;
	Sprite[] sprites;
	int [][] map;
	ShapeRenderer shapeRenderer;
	int sprSize = 32;
	
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		map = new int[20][20];
		batch = new SpriteBatch();
		img = new Texture("WaveColaps.png");
		sprites = new Sprite[3*6];//img_sprs("WaveColaps.png")

		int i = 0;
		for (Sprite s : img_sprs("WaveColaps.png")){
			sprites[i] = s;
			i++;
		}
		System.out.println(Arrays.deepToString(map = WaveCollapseFunction.createMap(map.length)));
		for (i = 0; i < map.length; i++){
			System.out.println(Arrays.toString(map[i]));
		}
	}

	@Override
	public void render () {

		ScreenUtils.clear(.5f, .5f, .5f, 1);
		batch.begin();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.BLUE);
		for(int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++){
				sprites[map[j][i]].setPosition(i*sprSize,Gdx.graphics.getHeight() - sprSize - j*sprSize);
				sprites[map[j][i]].setSize(sprSize, sprSize);
				shapeRenderer.rect(i*sprSize,Gdx.graphics.getHeight() - sprSize - j*sprSize, sprSize, sprSize);
				sprites[map[j][i]].draw(batch);
			}
/*
		int i = 0;
		for (Sprite s: sprites){
			s.setPosition(i*16, 0);
			shapeRenderer.rect(i*16,0, 16,16);
			s.draw(batch);
			i++;
		}

 */

		batch.end();
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public static Texture[] img_split(String ing_name){
		Texture wave_textures = new Texture(Gdx.files.internal(ing_name));
		TextureRegion[][] tmp = TextureRegion.split(wave_textures, 16, 16);
		Texture[] textures = new Texture[3 * 6];
		int index = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				textures[index++] = tmp[i][j].getTexture();
			}
		}
		return textures;
	}
	public static TextureRegion[][] img_split2(String ing_name){
		Texture wave_textures = new Texture(Gdx.files.internal(ing_name));
		TextureRegion[][] tmp = TextureRegion.split(wave_textures, 16, 16);
		return tmp;
	}
	public static Sprite[] img_sprs(String ing_name){
		Texture wave_textures = new Texture(Gdx.files.internal(ing_name));
		int height = wave_textures.getHeight()/16;
		int width = wave_textures.getWidth()/16;
		Sprite[] sprites = new Sprite[height*width];
		int index = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				sprites[index++] = new Sprite(wave_textures, 16*j, 16*i, 16, 16);
			}
		}
		return sprites;
	}
}
