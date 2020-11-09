/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import React from 'react';

import antenna from './images/antenna.svg';
import camera from './images/camera.svg';
import chipsetStandard from './images/chipset_standard.svg';
import chipset2Standard from './images/chipset2_standard.svg';
import chipset2High from './images/chipset2_high.svg';
import cpuStandard from './images/cpu_standard.svg';
import fan from './images/fan.svg';
import radar from './images/radar.svg';

const diagram = {
  id: '036f0cb1-b87c-40dd-b185-41ef9380066a',
  targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#/',
  label: 'Topography',
  descriptionId: "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']",
  position: { x: 0, y: 0 },
  size: { width: 887.6796875, height: 507.2736970610124 },
  tools: [
    {
      id:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@toolSections.0/@ownedTools[name='Composite%20Processor']",
      imageURL: 'http://localhost:8080/api/images/icons/full/obj16/System.gif',
      label: 'Composite Processor',
    },
    {
      id:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@toolSections.0/@ownedTools[name='CreateFan']",
      imageURL: 'http://localhost:8080/api/images/icons/full/obj16/Fan.gif',
      label: 'Fan',
    },
    {
      id:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@toolSections.0/@ownedTools[name='Processor']",
      imageURL: 'http://localhost:8080/api/images/icons/full/obj16/Processor_active.gif',
      label: 'Processor',
    },
    {
      id:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@toolSections.0/@ownedTools[name='Data%20Source']",
      imageURL: 'http://localhost:8080/api/images/icons/full/obj16/DataSource_active.gif',
      label: 'Data Source',
    },
  ],
  nodes: [
    {
      id: '15493a2f-5e45-4a19-993b-9be66f183ef6',
      type: 'node:image',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.2',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@nodeMappings[name='Data%20Source']",
      label: {
        id: '15493a2f-5e45-4a19-993b-9be66f183ef6_label',
        type: 'label:inside-center',
        text: 'Wifi : 4',
        style: { color: '#002b3c', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 17.978515625, y: -14.3125 },
        size: { width: 28.04296875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      style: { imageURL: antenna },
      position: { x: 349.75, y: 417.4548654789557 },
      size: { width: 64, height: 77.81883158205669 },
      borderNodes: [],
      childNodes: [],
    },
    {
      id: 'db535b9e-821a-440b-87e6-a01fd064bcb1',
      type: 'node:rectangle',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']",
      label: {
        id: 'db535b9e-821a-440b-87e6-a01fd064bcb1_label',
        type: 'label:inside-center',
        text: 'Central_Unit (25°C)',
        style: { color: '#002b3c', fontSize: 10, bold: true, italic: false, underline: false, strikeThrough: false },
        position: { x: 107.75634765625, y: 5 },
        size: { width: 108.4521484375, height: 11.640625 },
        alignment: { x: 0, y: 9.2822265625 },
      },
      style: { color: '#f0f0f0', borderColor: '#b1bcbe', borderStyle: 'Solid', borderSize: 1 },
      position: { x: 551.71484375, y: 182.54891721007868 },
      size: { width: 323.96484375, height: 204.765625 },
      borderNodes: [],
      childNodes: [
        {
          id: 'a8b7764f-1a5b-457e-ba70-6398493cfb37',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.0/@elements.0',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'a8b7764f-1a5b-457e-ba70-6398493cfb37_label',
            type: 'label:inside-center',
            text: 'DSP',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 23.96875, y: -14.3125 },
            size: { width: 16.0625, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: chipsetStandard },
          position: { x: 86, y: 81.453125 },
          size: { width: 64, height: 64 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '58ff3388-1802-4308-ba37-e4ada6564a51',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.0/@elements.1',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: '58ff3388-1802-4308-ba37-e4ada6564a51_label',
            type: 'label:inside-center',
            text: 'Motion_Engine',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 17.73046875, y: -14.3125 },
            size: { width: 58.5390625, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: chipset2High },
          position: { x: 217.96484375, y: 42.953125 },
          size: { width: 94, height: 94 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'ff1da9a2-8310-47fe-846f-ca17b4ff8112',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.0/@elements.2',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Fan']",
          label: {
            id: 'ff1da9a2-8310-47fe-846f-ca17b4ff8112_label',
            type: 'label:inside-center',
            text: '100',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 19.365234375, y: -14.3125 },
            size: { width: 15.26953125, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: fan },
          position: { x: 12, y: 138.765625 },
          size: { width: 54, height: 54 },
          borderNodes: [],
          childNodes: [],
        },
      ],
    },
    {
      id: '018723e0-b086-4b4d-93f0-16d421d36cc4',
      type: 'node:rectangle',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']",
      label: {
        id: '018723e0-b086-4b4d-93f0-16d421d36cc4_label',
        type: 'label:inside-center',
        text: 'CaptureSubSystem (28°C)',
        style: { color: '#002b3c', fontSize: 10, bold: true, italic: false, underline: false, strikeThrough: false },
        position: { x: 127.43994140625, y: 5 },
        size: { width: 146.8701171875, height: 11.640625 },
        alignment: { x: 0, y: 9.2822265625 },
      },
      style: { color: '#f0f0f0', borderColor: '#b1bcbe', borderStyle: 'Solid', borderSize: 1 },
      position: { x: 12, y: 12 },
      size: { width: 401.75, height: 341.1423654789557 },
      borderNodes: [],
      childNodes: [
        {
          id: '9f0e8ee4-9516-4f03-938c-874902eeb0de',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.0',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: '9f0e8ee4-9516-4f03-938c-874902eeb0de_label',
            type: 'label:inside-center',
            text: 'Radar_Capture',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 12.142578125, y: -14.3125 },
            size: { width: 59.71484375, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: cpuStandard },
          position: { x: 168.875, y: 53.59492197060084 },
          size: { width: 84, height: 84 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '5dec0569-de15-4bc9-b69d-47228b354561',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.3',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: '5dec0569-de15-4bc9-b69d-47228b354561_label',
            type: 'label:inside-center',
            text: 'Engine',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 28.291015625, y: -14.3125 },
            size: { width: 27.41796875, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: chipset2Standard },
          position: { x: 305.75, y: 106.90742197060084 },
          size: { width: 84, height: 84 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.4',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1_label',
            type: 'label:inside-center',
            text: 'GPU',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 33.560546875, y: -14.3125 },
            size: { width: 16.87890625, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: cpuStandard },
          position: { x: 168.875, y: 184.68954221007868 },
          size: { width: 84, height: 84 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '9aeec72b-c97b-427a-88db-687d0b43f9c4',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.1',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Data%20Source']",
          label: {
            id: '9aeec72b-c97b-427a-88db-687d0b43f9c4_label',
            type: 'label:inside-center',
            text: 'Back_Camera: 6',
            style: {
              color: '#002b3c',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 9.427734375, y: -14.3125 },
            size: { width: 65.14453125, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: camera },
          position: { x: 32, y: 182.54921894120167 },
          size: { width: 84, height: 88.28064653775401 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '24aea295-5710-473d-a0b9-6f2bdd15733a',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.2',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Data%20Source']",
          label: {
            id: '24aea295-5710-473d-a0b9-6f2bdd15733a_label',
            type: 'label:inside-center',
            text: 'Radar: 8',
            style: {
              color: '#002b3c',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 34.970703125, y: -14.3125 },
            size: { width: 34.05859375, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: radar },
          position: { x: 12, y: 42.953125 },
          size: { width: 104, height: 105.28359394120169 },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: '2b4a0326-b2b7-4ad3-bfb1-67e754631905',
          type: 'node:image',
          targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.5',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Fan']",
          label: {
            id: '2b4a0326-b2b7-4ad3-bfb1-67e754631905_label',
            type: 'label:inside-center',
            text: '20',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
            },
            position: { x: 6.91015625, y: -14.3125 },
            size: { width: 10.1796875, height: 9.3125 },
            alignment: { x: 0, y: 7.42578125 },
          },
          style: { imageURL: fan },
          position: { x: 52, y: 305.1423654789557 },
          size: { width: 24, height: 24 },
          borderNodes: [],
          childNodes: [],
        },
      ],
    },
  ],
  edges: [
    {
      id: '9aeec72b-c97b-427a-88db-687d0b43f9c4 --> 36bf5f69-a92d-44f1-8782-2d6001b3c3e1 - 0',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.1/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: '9aeec72b-c97b-427a-88db-687d0b43f9c4',
      targetId: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1',
      beginLabel: null,
      centerLabel: {
        id: '9aeec72b-c97b-427a-88db-687d0b43f9c4 --> 36bf5f69-a92d-44f1-8782-2d6001b3c3e1 - 0_centerlabel',
        type: 'label:inside-center',
        text: '6/6',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 148, y: 241.68954221007868 },
        size: { width: 12.875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Dash', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 128, y: 238.68954221007868 },
        { x: 180.875, y: 238.68954221007868 },
      ],
    },
    {
      id: '24aea295-5710-473d-a0b9-6f2bdd15733a --> 9f0e8ee4-9516-4f03-938c-874902eeb0de - 1',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.2/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: '24aea295-5710-473d-a0b9-6f2bdd15733a',
      targetId: '9f0e8ee4-9516-4f03-938c-874902eeb0de',
      beginLabel: null,
      centerLabel: {
        id: '24aea295-5710-473d-a0b9-6f2bdd15733a --> 9f0e8ee4-9516-4f03-938c-874902eeb0de - 1_centerlabel',
        type: 'label:inside-center',
        text: '8/6',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 148, y: 95.28242197060084 },
        size: { width: 12.875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Dash', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 128, y: 107.59492197060084 },
        { x: 180.875, y: 107.59492197060084 },
      ],
    },
    {
      id: '15493a2f-5e45-4a19-993b-9be66f183ef6 --> a8b7764f-1a5b-457e-ba70-6398493cfb37 - 2',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.2/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: '15493a2f-5e45-4a19-993b-9be66f183ef6',
      targetId: 'a8b7764f-1a5b-457e-ba70-6398493cfb37',
      beginLabel: null,
      centerLabel: {
        id: '15493a2f-5e45-4a19-993b-9be66f183ef6 --> a8b7764f-1a5b-457e-ba70-6398493cfb37 - 2_centerlabel',
        type: 'label:inside-center',
        text: '4/4',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 471.294921875, y: 459.364281269984 },
        size: { width: 12.875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Dash', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 413.75, y: 456.364281269984 },
        { x: 516.71484375, y: 456.364281269984 },
        { x: 516.71484375, y: 296.0020422100787 },
        { x: 637.71484375, y: 296.0020422100787 },
      ],
    },
    {
      id: 'a8b7764f-1a5b-457e-ba70-6398493cfb37 --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 0',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.0/@elements.0/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: 'a8b7764f-1a5b-457e-ba70-6398493cfb37',
      targetId: '58ff3388-1802-4308-ba37-e4ada6564a51',
      beginLabel: null,
      centerLabel: {
        id: 'a8b7764f-1a5b-457e-ba70-6398493cfb37 --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 0_centerlabel',
        type: 'label:inside-center',
        text: '4/10',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 721.71484375, y: 299.0020422100787 },
        size: { width: 17.96484375, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Solid', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 701.71484375, y: 296.0020422100787 },
        { x: 769.6796875, y: 296.0020422100787 },
      ],
    },
    {
      id: '9f0e8ee4-9516-4f03-938c-874902eeb0de --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 1',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.0/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: '9f0e8ee4-9516-4f03-938c-874902eeb0de',
      targetId: '58ff3388-1802-4308-ba37-e4ada6564a51',
      beginLabel: null,
      centerLabel: {
        id: '9f0e8ee4-9516-4f03-938c-874902eeb0de --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 1_centerlabel',
        type: 'label:inside-center',
        text: '8/6',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 471.294921875, y: 81.28242197060084 },
        size: { width: 12.875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Solid', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 264.875, y: 93.59492197060084 },
        { x: 516.71484375, y: 93.59492197060084 },
        { x: 516.71484375, y: 227.68954221007868 },
        { x: 759.6796875, y: 227.68954221007868 },
        { x: 759.6796875, y: 249.00204221007868 },
        { x: 769.6796875, y: 249.00204221007868 },
      ],
    },
    {
      id: '9f0e8ee4-9516-4f03-938c-874902eeb0de --> 5dec0569-de15-4bc9-b69d-47228b354561 - 2',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.0/@outgoingFlows.1',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: '9f0e8ee4-9516-4f03-938c-874902eeb0de',
      targetId: '5dec0569-de15-4bc9-b69d-47228b354561',
      beginLabel: null,
      centerLabel: {
        id: '9f0e8ee4-9516-4f03-938c-874902eeb0de --> 5dec0569-de15-4bc9-b69d-47228b354561 - 2_centerlabel',
        type: 'label:inside-center',
        text: '8/6',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 284.875, y: 163.90742197060084 },
        size: { width: 12.875, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Solid', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 264.875, y: 121.59492197060084 },
        { x: 274.875, y: 121.59492197060084 },
        { x: 274.875, y: 160.90742197060084 },
        { x: 317.75, y: 160.90742197060084 },
      ],
    },
    {
      id: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1 --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 3',
      type: 'edge:straight',
      targetObjectId: 'fb243c12-d9e0-45c8-83a3-046557339895#//@elements.1/@elements.4/@outgoingFlows.0',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1',
      targetId: '58ff3388-1802-4308-ba37-e4ada6564a51',
      beginLabel: null,
      centerLabel: {
        id: '36bf5f69-a92d-44f1-8782-2d6001b3c3e1 --> 58ff3388-1802-4308-ba37-e4ada6564a51 - 3_centerlabel',
        type: 'label:inside-center',
        text: '6/10',
        style: { color: '#000000', fontSize: 8, bold: false, italic: false, underline: false, strikeThrough: false },
        position: { x: 468.75, y: 241.68954221007868 },
        size: { width: 17.96484375, height: 9.3125 },
        alignment: { x: 0, y: 7.42578125 },
      },
      endLabel: null,
      style: { size: 1, lineStyle: 'Solid', sourceArrow: 'None', targetArrow: 'None', color: '#b1bcbe' },
      routingPoints: [
        { x: 264.875, y: 238.68954221007868 },
        { x: 749.6796875, y: 238.68954221007868 },
        { x: 749.6796875, y: 272.5020422100787 },
        { x: 769.6796875, y: 272.5020422100787 },
      ],
    },
  ],
};

const Diagram = ({ diagram }) => {
  const nodes = diagram.nodes.map((node) => {
    let element = undefined;
    if (node.type === 'node:rectangle') {
      element = <RectangleNode node={node} key={node.id} />;
    } else if (node.type === 'node:image') {
      element = <ImageNode node={node} key={node.id} />;
    }
    return element;
  });

  const edges = diagram.edges.map((edge) => {
    return <Edge key={edge.id} edge={edge} />;
  });
  return (
    <svg id={diagram.id} className="sprotty-graph" style={{ cursor: 'pointer', width: '100%', height: '100%' }}>
      <g transform="scale(1) translate(0, 0)">
        {nodes}
        {edges}
      </g>
    </svg>
  );
};

const Label = ({ label }) => {
  const { text, style, position, alignment } = label;
  const { color, bold, underline, strikeThrough, italic, fontSize } = style;

  const defaultStyle = {
    fill: color,
    fontSize: fontSize + 'px',
    fontFamily: 'Arial, Helvetic, sans-serif',
    fontWeight: 'normal',
    fontStyle: 'normal',
    textDecoration: 'none',
  };

  if (bold) {
    defaultStyle.fontWeight = 'bold';
  }
  if (italic) {
    defaultStyle.fontStyle = 'italic';
  }
  if (underline) {
    defaultStyle.textDecoration = 'underline';
  }
  if (strikeThrough) {
    if (defaultStyle.textDecoration === 'none') {
      defaultStyle.textDecoration = 'line-through';
    } else {
      defaultStyle.textDecoration += ' line-through';
    }
  }

  return (
    <text
      id={label.id}
      transform={`translate(${position.x}, ${position.y}) translate(${alignment.x}, ${alignment.y})`}
      style={defaultStyle}>
      {text}
    </text>
  );
};

const RectangleNode = ({ node }) => {
  const { position, size, style, label } = node;
  const { x, y } = position;
  const { width, height } = size;
  const { color, borderColor, borderSize } = style;

  const defaultStyle = {
    fill: color,
    stroke: borderColor,
    strokeWidth: borderSize,
  };

  const nodes = node.childNodes.map((childNode) => {
    let element = undefined;
    if (childNode.type === 'node:rectangle') {
      element = <RectangleNode node={childNode} key={childNode.id} />;
    } else if (childNode.type === 'node:image') {
      element = <ImageNode node={childNode} key={childNode.id} />;
    }
    return element;
  });

  return (
    <g id={node.id} transform={`translate(${x}, ${y})`} opacity="1">
      <rect x={0} y={0} width={width} height={height} style={defaultStyle}></rect>
      <Label label={label} />
      {nodes}
    </g>
  );
};

const ImageNode = ({ node }) => {
  const { position, size, style, label } = node;
  const { x, y } = position;
  const { width, height } = size;
  const { imageURL } = style;

  const defaultStyle = {};

  return (
    <g id={node.id} transform={`translate(${x}, ${y})`} opacity="1">
      <image x={0} y={0} width={width} height={height} href={imageURL} style={defaultStyle} />
      <Label label={label} />
    </g>
  );
};

const Edge = ({ edge }) => {
  const { style, routingPoints } = edge;

  const defaultStyle = {
    stroke: style.color,
    strokeWidth: style.size,
    fill: 'transparent',
  };

  const firstPoint = routingPoints[0];
  let path = `M ${firstPoint.x},${firstPoint.y}`;
  for (let i = 1; i < routingPoints.length; i++) {
    const point = routingPoints[i];
    path += `L ${point.x}, ${point.y}`;
  }

  return (
    <g>
      <path className="sprotty-edge" d={path} style={defaultStyle} />
    </g>
  );
};

export const DiagramRepresentationStory = () => {
  return (
    <div style={{ width: '100vw', height: '100vh', display: 'grid' }}>
      <Diagram diagram={diagram} />
    </div>
  );
};
