/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { useTheme } from '@material-ui/core/styles';
import { useCallback } from 'react';
import { Edge, EdgeMarkerType, ReactFlowState, useStore } from 'reactflow';
import { MarkerProps } from './MarkerDefinitions.types';

const markerSelector = (state: ReactFlowState): MarkerProps[] => {
  const ids: string[] = [];
  const initialMarkerProps: MarkerProps[] = [];
  const reducer = (allMarkerProps: MarkerProps[], edge: Edge): MarkerProps[] => {
    [edge.markerStart, edge.markerEnd].forEach((marker: EdgeMarkerType | undefined) => {
      if (marker && typeof marker === 'string' && edge.style?.stroke) {
        if (!ids.includes(marker)) {
          allMarkerProps.push(
            {
              id: marker,
              edgeColor: edge.style.stroke,
            },
            {
              id: `${marker}--selected`,
              edgeColor: edge.style.stroke,
            }
          );
          ids.push(marker);
          ids.push(`${marker}--selected`);
        }
      }
    });

    return allMarkerProps;
  };

  return state.edges
    .reduce<MarkerProps[]>(reducer, initialMarkerProps)
    .sort((firstMarkerProps, secondMarkerProps) => firstMarkerProps.id.localeCompare(secondMarkerProps.id));
};

const equalityFunction = (oldMarkerProps: MarkerProps[], newMarkerProps: MarkerProps[]) => {
  return !(
    oldMarkerProps.length !== newMarkerProps.length ||
    oldMarkerProps.some((edge, index) => !!newMarkerProps[index] && edge.id !== newMarkerProps[index]?.id)
  );
};

const getMarker = (markerProps: MarkerProps) => {
  const markerType = markerProps.id.split('--')[0];
  switch (markerType) {
    case 'None':
      return null;
    case 'OutputArrow':
      return <OutputArrow {...markerProps} key={markerProps.id} />;
    case 'InputArrow':
      return <InputArrow {...markerProps} key={markerProps.id} />;
    case 'OutputClosedArrow':
      return <OutputClosedArrow {...markerProps} key={markerProps.id} />;
    case 'InputClosedArrow':
      return <InputClosedArrow {...markerProps} key={markerProps.id} />;
    case 'OutputFillClosedArrow':
      return <OutputFillClosedArrow {...markerProps} key={markerProps.id} />;
    case 'InputFillClosedArrow':
      return <InputFillClosedArrow {...markerProps} key={markerProps.id} />;
    case 'Diamond':
      return <Diamond {...markerProps} key={markerProps.id} />;
    case 'FillDiamond':
      return <FillDiamond {...markerProps} key={markerProps.id} />;
    case 'InputArrowWithDiamond':
      return <InputArrowWithDiamond {...markerProps} key={markerProps.id} />;
    case 'InputArrowWithFillDiamond':
      return <InputArrowWithFillDiamond {...markerProps} key={markerProps.id} />;
    case 'Circle':
      return <Circle {...markerProps} key={markerProps.id} />;
    case 'FillCircle':
      return <FillCircle {...markerProps} key={markerProps.id} />;
    case 'CrossedCircle':
      return <CrossedCircle {...markerProps} key={markerProps.id} />;
    case 'ClosedArrowWithVerticalBar':
      return <ClosedArrowWithVerticalBar {...markerProps} key={markerProps.id} />;
    case 'ClosedArrowWithDots':
      return <ClosedArrowWithDots {...markerProps} key={markerProps.id} />;
    default:
      return <InputFillClosedArrow {...markerProps} key={markerProps.id} />;
  }
};

const OutputArrowPath = 'm 10 1 L 1 5 M 1 5 L 10 9 z';
const InputArrowPath = 'm 1 1 L 10 5 M 10 5 L 1 9 z';
const OutputClosedArrowPath = 'm 10 1 L 1 5 L 9.5 9 L 9.5 1.75 z';
const InputClosedArrowPath = 'm 1 1 L 10 5 L 1 9 L 1 1 z';
const DiamondPath = 'm 8 1 L 1 5 L 8 9 L 15 5 L 8 1 z';

const buildMarkerAttributes = (
  id: string,
  markerWidth: number,
  markerHeight: number,
  refX: number,
  refY: number
): React.SVGProps<SVGMarkerElement> => {
  return {
    id: id,
    markerUnits: 'strokeWidth',
    markerWidth: markerWidth,
    markerHeight: markerHeight,
    refX: refX,
    refY: refY,
    orient: 'auto-start-reverse',
  };
};

const buildArrow = (id: string, path: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <path fill={fill ? edgeColor : 'white'} d={path} stroke={edgeColor} strokeWidth={1} />
    </marker>
  );
};

const buildDiamond = (id: string, path: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 16, 10, 15, 5)}>
      <path fill={fill ? edgeColor : 'white'} d={path} stroke={edgeColor} strokeWidth={1} />
    </marker>
  );
};

const buildCircle = (id: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <circle r={4.5} cx={5} cy={5} fill={fill ? edgeColor : 'white'} stroke={edgeColor} strokeWidth={1} />
    </marker>
  );
};

export const MarkerDefinitions = () => {
  const selector = useCallback(markerSelector, []);
  const allMarkerProps = useStore<MarkerProps[]>(selector, equalityFunction);
  return (
    <svg id="edge-markers" style={{ position: 'absolute', zIndex: -1, top: '0px' }}>
      <defs>{allMarkerProps.map((markerProps) => getMarker(markerProps))}</defs>
    </svg>
  );
};

const OutputArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, OutputArrowPath, strokeColor, false);
};

const InputArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, InputArrowPath, strokeColor, false);
};

const OutputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, OutputClosedArrowPath, strokeColor, false);
};

const InputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, InputClosedArrowPath, strokeColor, false);
};

const OutputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, OutputClosedArrowPath, strokeColor, true);
};

const InputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildArrow(id, InputClosedArrowPath, strokeColor, true);
};

const Diamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildDiamond(id, DiamondPath, strokeColor, false);
};

const FillDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildDiamond(id, DiamondPath, strokeColor, true);
};

const Circle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildCircle(id, strokeColor, false);
};

const FillCircle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return buildCircle(id, strokeColor, true);
};

const CrossedCircle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <circle r={4.5} cx={5} cy={5} stroke={strokeColor} fill={'white'} />
      <path d="M 1 5 L 9 5" stroke={strokeColor} strokeWidth={1} />
      <path d="m 5 1 L 5 9" stroke={strokeColor} strokeWidth={1} />
    </marker>
  );
};

const InputArrowWithDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 24, 10, 24, 5)}>
      <path d={InputArrowPath} stroke={strokeColor} strokeWidth={1} />
      <path d="m 17 1 L 10 5 L 17 9 L 24 5 L 17 1 z" stroke={strokeColor} fill={'white'} strokeWidth={1} />
    </marker>
  );
};

const InputArrowWithFillDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 24, 10, 24, 5)}>
      <path d={InputArrowPath} stroke={strokeColor} strokeWidth={1} />
      <path d="m 17 1 L 10 5 L 17 9 L 24 5 L 17 1 z" stroke={strokeColor} fill={strokeColor} strokeWidth={1} />
    </marker>
  );
};

const ClosedArrowWithVerticalBar = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 14, 10, 14, 5)}>
      <path strokeWidth={1} fill={'white'} d={'m 5 1 L 14 5 L 5 9 L 5 1 z'} stroke={strokeColor} />
      <path fill={strokeColor} d={'m 1 10 V 0'} stroke={strokeColor} />
    </marker>
  );
};

const ClosedArrowWithDots = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = id.endsWith('--selected') ? theme.palette.primary.main : getCSSColor(edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 14, 10, 14, 5)}>
      <path strokeWidth={1} fill={'white'} d={'m 5 1 L 14 5 L 5 9 L 5 1 z'} stroke={strokeColor} />
      <circle r={1} cx={1} cy={3} fill={strokeColor} stroke={'none'} />
      <circle r={1} cx={1} cy={7} fill={strokeColor} stroke={'none'} />
    </marker>
  );
};
