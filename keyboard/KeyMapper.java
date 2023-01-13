/**
 * Title: KeyMapper
 * Copyright:   Copyright (c) 2001
 * Company:
 * @author  Kenneth J. Pouncey
 * @version 0.1
 *
 * Description:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software; see the file COPYING.  If not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 */
package tn5250j.keyboard;

import tn5250j.event.KeyChangeListener;
import tn5250j.interfaces.ConfigureFactory;
import tn5250j.interfaces.OptionAccessFactory;
import tn5250j.keyboard.KeyStroker;
import tn5250j.tools.LangTool;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

public class KeyMapper {

	private static HashMap<tn5250j.keyboard.KeyStroker, String> mappedKeys;
	private static tn5250j.keyboard.KeyStroker workStroke;
	private static String lastKeyMnemonic;
	private static Vector<KeyChangeListener> listeners;
	private static boolean useJava14;

	public static void init() {

		if (mappedKeys != null)
			return;

		mappedKeys = new HashMap<tn5250j.keyboard.KeyStroker, String>(60);
		workStroke = new tn5250j.keyboard.KeyStroker(0, false, false, false,false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD);

		Properties keys = ConfigureFactory.getInstance().getProperties(
				ConfigureFactory.KEYMAP);

		if (!loadKeyStrokes(keys)) {
			// keycode shift control alternate

			// Key <-> Keycode , isShiftDown , isControlDown , isAlternateDown, location

			// my personal preference
			// mappedKeys.put(new KeyStroker(10, false, false, false, false,KeyStroker.KEY_LOCATION_STANDARD),"[fldext]");

			if (useJava14) {
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(10, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[enter]");
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(10, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_NUMPAD),"[enter].alt2");
			}
			else
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(17, false, true, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[enter]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(8, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[backspace]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(9, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[tab]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(9, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[backtab]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(127, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[delete]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(155, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[insert]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(19, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[clear]");

			if (useJava14)
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(17, false, true, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_LEFT),"[reset]");
			else
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(27, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[reset]");

			if (useJava14)
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(27, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[sysreq]");
			else
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(27, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[sysreq]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(35, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[eof]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(36, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[home]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(39, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[right]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(39, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[nextword]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(37, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[left]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(37, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[prevword]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(38, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[up]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(40, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[down]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(34, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pgdown]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(33, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pgup]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(96, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad0]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(97, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad1]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(98, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad2]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(99, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad3]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(100, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad4]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(101, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad5]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(102, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad6]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(103, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad7]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(104, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad8]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(105, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[keypad9]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(109, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[field-]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(107, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[field+]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(112, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf1]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(113, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf2]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(114, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf3]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(115, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf4]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(116, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf5]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(117, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf6]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(118, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf7]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(119, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf8]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(120, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf9]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(121, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf10]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(122, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf11]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(123, false, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf12]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(112, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf13]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(113, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf14]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(114, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf15]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(115, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf16]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(116, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf17]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(117, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf18]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(118, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf19]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(119, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf20]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(120, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf21]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(121, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf22]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(122, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf23]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(123, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[pf24]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(112, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[help]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(72, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[hostprint]");

			if (useJava14)
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(67, false, true, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[copy]");
			else
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(67, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[copy]");

			if (useJava14)
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(86, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[paste]");
			else
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(86, false, true, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[paste]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(39, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[markright]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(37, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[markleft]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(38, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[markup]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(40, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[markdown]");

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(155, true, false, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[dupfield]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(17, true, true, false, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[newline]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(34, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[jumpnext]");
			mappedKeys.put(new tn5250j.keyboard.KeyStroker(33, false, false, true, false, tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD),"[jumpprev]");

			saveKeyMap();
		}
		else {

			setKeyMap(keys);

		}

	}


	private static boolean loadKeyStrokes(Properties keystrokes) {

		keystrokes = ConfigureFactory.getInstance().getProperties(ConfigureFactory.KEYMAP);
		if (keystrokes != null && keystrokes.size() > 0)
			return true;
		else
			return false;
	}

	private static void parseKeyStrokes(Properties keystrokes) {

		String theStringList = "";
		String theKey = "";
		Enumeration<?> ke = keystrokes.propertyNames();
		while (ke.hasMoreElements()) {
			theKey = (String)ke.nextElement();

			if (OptionAccessFactory.getInstance().isRestrictedOption(theKey)) {
				continue;
			}

			theStringList = keystrokes.getProperty(theKey);
			int kc = 0;
			boolean is = false;
			boolean ic = false;
			boolean ia = false;
			boolean iag = false;
			int location = tn5250j.keyboard.KeyStroker.KEY_LOCATION_STANDARD;

			StringTokenizer tokenizer = new StringTokenizer(theStringList, ",");

			// first is the keycode
			kc = Integer.parseInt(tokenizer.nextToken());
			// isShiftDown
			if (tokenizer.nextToken().equals("true"))
				is = true;
			else
				is =false;
			// isControlDown
			if (tokenizer.nextToken().equals("true"))
				ic = true;
			else
				ic =false;
			// isAltDown
			if (tokenizer.nextToken().equals("true"))
				ia = true;
			else
				ia =false;

			// isAltDown Gr
			if (tokenizer.hasMoreTokens()) {
				if (tokenizer.nextToken().equals("true"))
					iag = true;
				else
					iag =false;

				if (tokenizer.hasMoreTokens()) {
					location = Integer.parseInt(tokenizer.nextToken());
				}
			}

			mappedKeys.put(new tn5250j.keyboard.KeyStroker(kc, is, ic, ia, iag,location),theKey);

		}

	}

	protected static void setKeyMap(Properties keystrokes) {

		parseKeyStrokes(keystrokes);

	}

	public final static boolean isEqualLast(KeyEvent ke) {
		return workStroke.equals(ke);
	}

	public final static void saveKeyMap() {

		Properties map = ConfigureFactory.getInstance().getProperties(ConfigureFactory.KEYMAP);

		map.clear();

		// save off the keystrokes in the keymap
		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			map.put(i.next(),ks.toString());
		}

		ConfigureFactory.getInstance().saveSettings(ConfigureFactory.KEYMAP,
				"------ Key Map key=keycode,isShiftDown,isControlDown,isAltDown,isAltGrDown,location --------");
	}

	public final static String getKeyStrokeText(KeyEvent ke) {
		return getKeyStrokeText(ke,false);
	}

	public final static String getKeyStrokeText(KeyEvent ke,boolean isAltGr) {
		if (!workStroke.equals(ke,isAltGr)) {
			workStroke.setAttributes(ke,isAltGr);
			lastKeyMnemonic = mappedKeys.get(workStroke);
		}

		if (lastKeyMnemonic != null &&
				lastKeyMnemonic.endsWith(tn5250j.keyboard.KeyStroker.altSuffix)) {

			lastKeyMnemonic = lastKeyMnemonic.substring(0,
					lastKeyMnemonic.indexOf(tn5250j.keyboard.KeyStroker.altSuffix));
		}

		return lastKeyMnemonic;

	}

	public final static String getKeyStrokeMnemonic(KeyEvent ke) {
		return getKeyStrokeMnemonic(ke,false);
	}

	public final static String getKeyStrokeMnemonic(KeyEvent ke,boolean isAltGr) {

		workStroke.setAttributes(ke,isAltGr);
		String keyMnemonic = mappedKeys.get(workStroke);

		if (keyMnemonic != null &&
				keyMnemonic.endsWith(tn5250j.keyboard.KeyStroker.altSuffix)) {

			keyMnemonic = keyMnemonic.substring(0,
					keyMnemonic.indexOf(tn5250j.keyboard.KeyStroker.altSuffix));
		}

		return keyMnemonic;

	}

	public final static int getKeyStrokeCode() {
		return workStroke.hashCode();
	}

	public final static String getKeyStrokeDesc(String which) {

		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which))
				return ks.getKeyStrokeDesc();
		}

		return LangTool.getString("key.dead");
	}

	public final static tn5250j.keyboard.KeyStroker getKeyStroker(String which) {

		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which))
				return ks;
		}

		return null;
	}

	public final static boolean isKeyStrokeDefined(String which) {

		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			k.next();
			String keyVal = i.next();
			if (keyVal.equals(which))
				return true;
		}

		return false;
	}

	public final static boolean isKeyStrokeDefined(KeyEvent ke) {
		return isKeyStrokeDefined(ke,false);
	}

	public final static boolean isKeyStrokeDefined(KeyEvent ke,boolean isAltGr) {

		workStroke.setAttributes(ke,isAltGr);
		return (null != mappedKeys.get(workStroke));

	}

	public final static KeyStroke getKeyStroke(String which) {

		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which)) {
				int mask = 0;

				if (ks.isShiftDown())
					mask |= InputEvent.SHIFT_MASK;
				if (ks.isControlDown())
					mask |= InputEvent.CTRL_MASK;
				if (ks.isAltDown())
					mask |= InputEvent.ALT_MASK;
				if (ks.isAltGrDown())
					mask |= InputEvent.ALT_GRAPH_MASK;

				return KeyStroke.getKeyStroke(ks.getKeyCode(),mask);
			}
		}

		return KeyStroke.getKeyStroke(0,0);
	}

	public final static void removeKeyStroke(String which) {

		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which)) {
				mappedKeys.remove(ks);
				return;
			}
		}

	}

	public final static void setKeyStroke(String which, KeyEvent ke) {

		if (ke == null)
			return;
		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which)) {
				mappedKeys.remove(ks);
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(ke),keyVal);
				return;
			}
		}

		// if we got here it was a dead key and we need to add it.
		mappedKeys.put(new tn5250j.keyboard.KeyStroker(ke),which);

	}

	public final static void setKeyStroke(String which, KeyEvent ke, boolean isAltGr) {

		if (ke == null)
			return;
		Collection<String> v = mappedKeys.values();
		Set<tn5250j.keyboard.KeyStroker> o = mappedKeys.keySet();
		Iterator<tn5250j.keyboard.KeyStroker> k = o.iterator();
		Iterator<String> i = v.iterator();
		while (k.hasNext()) {
			tn5250j.keyboard.KeyStroker ks = k.next();
			String keyVal = i.next();
			if (keyVal.equals(which)) {
				mappedKeys.remove(ks);
				mappedKeys.put(new tn5250j.keyboard.KeyStroker(ke, isAltGr), keyVal);
				return;
			}
		}

		// if we got here it was a dead key and we need to add it.
		mappedKeys.put(new tn5250j.keyboard.KeyStroker(ke, isAltGr), which);

	}

	public final static HashMap<KeyStroker, String> getKeyMap() {
		return mappedKeys;
	}

	/**
	 * Add a KeyChangeListener to the listener list.
	 *
	 * @param listener  The KeyChangedListener to be added
	 */
	public static synchronized void addKeyChangeListener(KeyChangeListener listener) {

		if (listeners == null) {
			listeners = new Vector<KeyChangeListener>(3);
		}
		listeners.addElement(listener);

	}

	/**
	 * Remove a Key Change Listener from the listener list.
	 *
	 * @param listener  The KeyChangeListener to be removed
	 */
	public synchronized void removeKeyChangeListener(KeyChangeListener listener) {
		if (listeners == null) {
			return;
		}
		listeners.removeElement(listener);

	}

	/**
	 * Notify all registered listeners of the Key Change Event.
	 *
	 */
	public static void fireKeyChangeEvent() {

		if (listeners != null) {
			int size = listeners.size();
			for (int i = 0; i < size; i++) {
				KeyChangeListener target =
					listeners.elementAt(i);
				target.onKeyChanged();
			}
		}
	}

}
