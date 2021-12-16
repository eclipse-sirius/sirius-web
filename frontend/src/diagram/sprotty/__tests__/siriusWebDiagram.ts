/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import {
  GQLArrowStyle,
  GQLDiagram,
  GQLImageNodeStyle,
  GQLLineStyle,
  GQLRectangularNodeStyle,
} from 'diagram/DiagramWebSocketContainer.types';

export const siriusWebDiagram: GQLDiagram = {
  id: 'bf6a7cc1-011d-4900-8fa1-16b575a71175',
  kind: 'Diagram',
  targetObjectId: 'robot#/',
  label: 'Topography',
  descriptionId: "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']",
  __typename: 'Diagram',
  position: {
    x: 0,
    y: 0,
  },
  size: {
    width: 781,
    height: 506.6789704985124,
  },
  nodes: [
    {
      id: 'robot#//@elements.2',
      type: 'node:image',
      targetObjectId: 'robot#//@elements.2',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@nodeMappings[name='Data%20Source']",
      label: {
        id: 'robot#//@elements.2_label',
        type: 'label:inside-center',
        text: 'Wifi : 4',
        style: {
          color: '#002b3c',
          fontSize: 8,
          bold: false,
          italic: false,
          underline: false,
          strikeThrough: false,
          iconURL: '/api/images/image.svg',
        },
        position: {
          x: 19.77734375,
          y: -14.19921875,
        },
        size: {
          width: 24.4453125,
          height: 9.19921875,
        },
        alignment: {
          x: 0,
          y: 7.2421875,
        },
      },
      style: {
        __typename: 'ImageNodeStyle',
        imageURL: '/api/images/images/antenna.svg',
      } as GQLImageNodeStyle,
      position: {
        x: 314,
        y: 416.8601389164557,
      },
      size: {
        width: 64,
        height: 77.81883158205669,
      },
      borderNodes: [],
      childNodes: [],
    },
    {
      id: 'robot#//@elements.0',
      type: 'node:rectangle',
      targetObjectId: 'robot#//@elements.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']",
      label: {
        id: 'robot#//@elements.0_label',
        type: 'label:inside-center',
        text: 'Central_Unit (25°C)',
        style: {
          color: '#002b3c',
          fontSize: 10,
          bold: true,
          italic: false,
          underline: false,
          strikeThrough: false,
          iconURL: '/api/images/image.svg',
        },
        position: {
          x: 97.38232421875,
          y: 5,
        },
        size: {
          width: 91.2353515625,
          height: 11.4990234375,
        },
        alignment: {
          x: 0,
          y: 9.052734375,
        },
      },
      style: {
        __typename: 'RectangularNodeStyle',
        color: '#f0f0f0',
        borderColor: '#b1bcbe',
        borderStyle: GQLLineStyle.Solid,
        borderSize: 1,
      } as GQLRectangularNodeStyle,
      position: {
        x: 483,
        y: 64.34101572060084,
      },
      size: {
        width: 286,
        height: 181.6982421875,
      },
      borderNodes: [
        {
          id: 'robot#//@elements.0/@powerOutputs.0',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.0/@powerOutputs.0',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@borderedNodeMappings[name='PowerOutputNode']",
          label: {
            id: 'robot#//@elements.0/@powerOutputs.0_label',
            type: 'label:inside-center',
            text: '',
            style: {
              color: '#000000',
              fontSize: 12,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 0,
              y: 0,
            },
            size: {
              width: 0,
              height: 13.798828125,
            },
            alignment: {
              x: 0,
              y: 10.86328125,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/power_output.svg',
          } as GQLImageNodeStyle,
          position: {
            x: -30,
            y: 139.6982421875,
          },
          size: {
            width: 30,
            height: 30,
          },
          borderNodes: [],
          childNodes: [],
        },
      ],
      childNodes: [
        {
          id: 'robot#//@elements.0/@elements.0',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.0/@elements.0',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'robot#//@elements.0/@elements.0_label',
            type: 'label:inside-center',
            text: 'DSP',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 23.775390625,
              y: -14.19921875,
            },
            size: {
              width: 16.44921875,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/chipset_standard.svg',
          } as GQLImageNodeStyle,
          position: {
            x: 86,
            y: 97.1982421875,
          },
          size: {
            width: 64,
            height: 64,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.0/@elements.1',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.0/@elements.1',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'robot#//@elements.0/@elements.1_label',
            type: 'label:inside-center',
            text: 'Motion_Engine',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 20.314453125,
              y: -14.19921875,
            },
            size: {
              width: 53.37109375,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/chipset2_high.svg',
          },
          position: {
            x: 180,
            y: 58.6982421875,
          },
          size: {
            width: 94,
            height: 94,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.0/@elements.2',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.0/@elements.2',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Fan']",
          label: {
            id: 'robot#//@elements.0/@elements.2_label',
            type: 'label:inside-center',
            text: '100',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 20.326171875,
              y: -14.19921875,
            },
            size: {
              width: 13.34765625,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/fan.svg',
          },
          position: {
            x: 12,
            y: 65.1982421875,
          },
          size: {
            width: 54,
            height: 54,
          },
          borderNodes: [],
          childNodes: [],
        },
      ],
    },
    {
      id: 'robot#//@elements.1',
      type: 'node:rectangle',
      targetObjectId: 'robot#//@elements.1',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']",
      label: {
        id: 'robot#//@elements.1_label',
        type: 'label:inside-center',
        text: 'CaptureSubSystem (28°C)',
        style: {
          color: '#002b3c',
          fontSize: 10,
          bold: true,
          italic: false,
          underline: false,
          strikeThrough: false,
          iconURL: '/api/images/image.svg',
        },
        position: {
          x: 105.98583984375,
          y: 5,
        },
        size: {
          width: 124.0283203125,
          height: 11.4990234375,
        },
        alignment: {
          x: 0,
          y: 9.052734375,
        },
      },
      style: {
        __typename: 'RectangularNodeStyle',
        color: '#f0f0f0',
        borderColor: '#b1bcbe',
        borderStyle: GQLLineStyle.Solid,
        borderSize: 1,
      } as GQLRectangularNodeStyle,
      position: {
        x: 42,
        y: 12,
      },
      size: {
        width: 336,
        height: 340.6609201664557,
      },
      borderNodes: [
        {
          id: 'robot#//@elements.1/@powerInputs.0',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@powerInputs.0',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@borderedNodeMappings[name='PowerInputNode']",
          label: {
            id: 'robot#//@elements.1/@powerInputs.0_label',
            type: 'label:inside-center',
            text: '',
            style: {
              color: '#000000',
              fontSize: 12,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 0,
              y: 0,
            },
            size: {
              width: 0,
              height: 13.798828125,
            },
            alignment: {
              x: 0,
              y: 10.86328125,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/power_input.svg',
          },
          position: {
            x: -30,
            y: 28.4990234375,
          },
          size: {
            width: 30,
            height: 30,
          },
          borderNodes: [],
          childNodes: [],
        },
      ],
      childNodes: [
        {
          id: 'robot#//@elements.1/@elements.0',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.0',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'robot#//@elements.1/@elements.0_label',
            type: 'label:inside-center',
            text: 'Radar_Capture',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 14.650390625,
              y: -14.19921875,
            },
            size: {
              width: 54.69921875,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/cpu_standard.svg',
          },
          position: {
            x: 136,
            y: 53.34003915810084,
          },
          size: {
            width: 84,
            height: 84,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.1/@elements.3',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.3',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'robot#//@elements.1/@elements.3_label',
            type: 'label:inside-center',
            text: 'Engine',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 29.544921875,
              y: -14.19921875,
            },
            size: {
              width: 24.91015625,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/chipset2_standard.svg',
          },
          position: {
            x: 240,
            y: 106.53925790810084,
          },
          size: {
            width: 84,
            height: 84,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.1/@elements.4',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.4',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Processor']",
          label: {
            id: 'robot#//@elements.1/@elements.4_label',
            type: 'label:inside-center',
            text: 'GPU',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 33.33203125,
              y: -14.19921875,
            },
            size: {
              width: 17.3359375,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/cpu_standard.svg',
          },
          position: {
            x: 136,
            y: 184.32137814757868,
          },
          size: {
            width: 84,
            height: 84,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.1/@elements.1',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.1',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Data%20Source']",
          label: {
            id: 'robot#//@elements.1/@elements.1_label',
            type: 'label:inside-center',
            text: 'Back_Camera: 6',
            style: {
              color: '#002b3c',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 12.208984375,
              y: -14.19921875,
            },
            size: {
              width: 59.58203125,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/camera.svg',
          },
          position: {
            x: 32,
            y: 182.18105487870167,
          },
          size: {
            width: 84,
            height: 88.28064653775401,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.1/@elements.2',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.2',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Data%20Source']",
          label: {
            id: 'robot#//@elements.1/@elements.2_label',
            type: 'label:inside-center',
            text: 'Radar: 8',
            style: {
              color: '#002b3c',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 36.658203125,
              y: -14.19921875,
            },
            size: {
              width: 30.68359375,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/radar.svg',
          },
          position: {
            x: 12,
            y: 42.6982421875,
          },
          size: {
            width: 104,
            height: 105.28359394120169,
          },
          borderNodes: [],
          childNodes: [],
        },
        {
          id: 'robot#//@elements.1/@elements.5',
          type: 'node:image',
          targetObjectId: 'robot#//@elements.1/@elements.5',
          targetObjectKind: 'flow::DataSource',
          targetObjectLabel: 'Robot',
          descriptionId:
            "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@containerMappings[name='System']/@subNodeMappings[name='Fan']",
          label: {
            id: 'robot#//@elements.1/@elements.5_label',
            type: 'label:inside-center',
            text: '20',
            style: {
              color: '#000000',
              fontSize: 8,
              bold: false,
              italic: false,
              underline: false,
              strikeThrough: false,
              iconURL: '/api/images/image.svg',
            },
            position: {
              x: 7.55078125,
              y: -14.19921875,
            },
            size: {
              width: 8.8984375,
              height: 9.19921875,
            },
            alignment: {
              x: 0,
              y: 7.2421875,
            },
          },
          style: {
            __typename: 'ImageNodeStyle',
            imageURL: '/api/images/images/fan.svg',
          },
          position: {
            x: 52,
            y: 304.6609201664557,
          },
          size: {
            width: 24,
            height: 24,
          },
          borderNodes: [],
          childNodes: [],
        },
      ],
    },
  ],
  edges: [
    {
      id: '007c10e5-0479-43b9-9418-3129ec300bec',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.1/@elements.1/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: 'robot#//@elements.1/@elements.1',
      targetId: 'robot#//@elements.1/@elements.4',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Dash,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 158,
          y: 238.32137814757868,
        },
        {
          x: 178,
          y: 238.32137814757868,
        },
      ],
    },
    {
      id: '21ed0822-c3c6-432f-b6e8-b006e9277e1e',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.1/@elements.2/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: 'robot#//@elements.1/@elements.2',
      targetId: 'robot#//@elements.1/@elements.0',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Dash,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 158,
          y: 107.34003915810084,
        },
        {
          x: 178,
          y: 107.34003915810084,
        },
      ],
    },
    {
      id: '140136fc-aaf2-413f-9a5a-63bba1fbd47a',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.2/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Data%20Source%20to%20Processor']",
      sourceId: 'robot#//@elements.2',
      targetId: 'robot#//@elements.0/@elements.0',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Dash,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 378,
          y: 455.769554707484,
        },
        {
          x: 423,
          y: 455.769554707484,
        },
        {
          x: 423,
          y: 193.53925790810084,
        },
        {
          x: 569,
          y: 193.53925790810084,
        },
      ],
    },
    {
      id: 'edf00d10-266e-4047-8a33-061f7e7badd5',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.0/@elements.0/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: 'robot#//@elements.0/@elements.0',
      targetId: 'robot#//@elements.0/@elements.1',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Solid,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 633,
          y: 193.53925790810084,
        },
        {
          x: 663,
          y: 193.53925790810084,
        },
      ],
    },
    {
      id: 'd76ffcad-c185-4a44-b04b-645938b9426c',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.1/@elements.0/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: 'robot#//@elements.1/@elements.0',
      targetId: 'robot#//@elements.0/@elements.1',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Solid,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 262,
          y: 93.34003915810084,
        },
        {
          x: 653,
          y: 93.34003915810084,
        },
        {
          x: 653,
          y: 146.53925790810084,
        },
        {
          x: 663,
          y: 146.53925790810084,
        },
      ],
    },
    {
      id: '118bc317-88f5-45d1-9c67-5983a5adb53b',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.1/@elements.0/@outgoingFlows.1',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: 'robot#//@elements.1/@elements.0',
      targetId: 'robot#//@elements.1/@elements.3',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Solid,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 262,
          y: 121.34003915810084,
        },
        {
          x: 272,
          y: 121.34003915810084,
        },
        {
          x: 272,
          y: 160.53925790810084,
        },
        {
          x: 282,
          y: 160.53925790810084,
        },
      ],
    },
    {
      id: '71a29dfc-3c77-4673-b5e5-ec2996035d9a',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.1/@elements.4/@outgoingFlows.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Processor%20%20to%20Processor']",
      sourceId: 'robot#//@elements.1/@elements.4',
      targetId: 'robot#//@elements.0/@elements.1',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Solid,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#b1bcbe',
      },
      routingPoints: [
        {
          x: 262,
          y: 238.32137814757868,
        },
        {
          x: 413,
          y: 238.32137814757868,
        },
        {
          x: 413,
          y: 104.34003915810084,
        },
        {
          x: 643,
          y: 104.34003915810084,
        },
        {
          x: 643,
          y: 170.03925790810084,
        },
        {
          x: 663,
          y: 170.03925790810084,
        },
      ],
    },
    {
      id: '61bb606e-89a3-42c4-b80e-8f7627ea1a48',
      type: 'edge:straight',
      targetObjectId: 'robot#//@elements.0/@powerOutputs.0',
      targetObjectKind: 'flow::DataSource',
      targetObjectLabel: 'Robot',
      descriptionId:
        "inmemory.odesign#//@ownedViewpoints[name='Exchanges']/@ownedRepresentations[name='Topography']/@defaultLayer/@edgeMappings[name='Link']",
      sourceId: 'robot#//@elements.0/@powerOutputs.0',
      targetId: 'robot#//@elements.1/@powerInputs.0',
      beginLabel: null,
      centerLabel: null,
      endLabel: null,
      style: {
        size: 1,
        lineStyle: GQLLineStyle.Dash,
        sourceArrow: GQLArrowStyle.None,
        targetArrow: GQLArrowStyle.None,
        color: '#002b3c',
      },
      routingPoints: [],
    },
  ],
};
