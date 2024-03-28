/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { Theme, useTheme } from '@material-ui/core/styles';
import { Edge, EdgeMarkerType, ReactFlowState, useStore } from '@xyflow/react';
import { useCallback } from 'react';
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

const OutputArrowPath = 'm 8.75 1 L 1.5 5 L 8.75 9';
const InputArrowPath = 'm 1 1 L 8 5 L 1 9';
const OutputClosedArrowPath = 'm 8.5 1 L 1.5 5 L 8.5 9 L 8.5 1 z';
const InputClosedArrowPath = 'm 1 1 L 8 5 L 1 9 L 1 1 z';
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

const buildArrow = (id: string, path: string, edgeColor: string, fillColor: string) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 8, 5)}>
      <path fill={fillColor ? fillColor : 'white'} d={path} stroke={edgeColor} strokeWidth={1} />
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
    <marker {...buildMarkerAttributes(id, 10, 10, 9, 5)}>
      <circle r={4.5} cx={5} cy={5} fill={fill ? edgeColor : 'white'} stroke={edgeColor} strokeWidth={1} />
    </marker>
  );
};

export const MarkerDefinitions = () => {
  const selector = useCallback(markerSelector, []);
  const allMarkerProps = useStore<MarkerProps[]>(selector, equalityFunction);
  return (
    <svg id="edge-markers" style={{ position: 'absolute', zIndex: -1, top: '0px' }}>
      <defs id="edge-markers-defs">{allMarkerProps.map((markerProps) => getMarker(markerProps))}</defs>
    </svg>
  );
};

const OutputArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, OutputArrowPath, strokeColor, theme.palette.background.default);
};

const InputArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, InputArrowPath, strokeColor, 'transparent');
};

const OutputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, OutputClosedArrowPath, strokeColor, theme.palette.background.default);
};

const InputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, InputClosedArrowPath, strokeColor, theme.palette.background.default);
};

const OutputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, OutputClosedArrowPath, strokeColor, strokeColor);
};

const InputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildArrow(id, InputClosedArrowPath, strokeColor, strokeColor);
};

const Diamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildDiamond(id, DiamondPath, strokeColor, false);
};

const FillDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildDiamond(id, DiamondPath, strokeColor, true);
};

const Circle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildCircle(id, strokeColor, false);
};

const FillCircle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return buildCircle(id, strokeColor, true);
};

const CrossedCircle = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 9, 5)}>
      <circle r={4.5} cx={5} cy={5} stroke={strokeColor} fill={theme.palette.background.default} />
      <path d="M 1 5 L 9 5" stroke={strokeColor} strokeWidth={1} />
      <path d="m 5 1 L 5 9" stroke={strokeColor} strokeWidth={1} />
    </marker>
  );
};

const InputArrowWithDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 24, 10, 23, 5)}>
      <path d={InputArrowPath} stroke={strokeColor} fill={'transparent'} strokeWidth={1} />
      <path
        d="m 16 1 L 9 5 L 16 9 L 23 5 L 16 1 z"
        stroke={strokeColor}
        fill={theme.palette.background.default}
        strokeWidth={1}
      />
    </marker>
  );
};

const InputArrowWithFillDiamond = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 24, 10, 23, 5)}>
      <path d={InputArrowPath} stroke={strokeColor} fill={'transparent'} strokeWidth={1} />
      <path d="m 16 1 L 9 5 L 16 9 L 23 5 L 16 1 z" stroke={strokeColor} fill={strokeColor} strokeWidth={1} />
    </marker>
  );
};

const ClosedArrowWithVerticalBar = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 13, 10, 12, 5)}>
      <path
        strokeWidth={1}
        fill={theme.palette.background.default}
        d={'m 5 1 L 12 5 L 5 9 L 5 1 z'}
        stroke={strokeColor}
      />
      <path fill={strokeColor} d={'m 1 10 V 0'} stroke={strokeColor} />
    </marker>
  );
};

const ClosedArrowWithDots = ({ id, edgeColor }: MarkerProps) => {
  const theme = useTheme();
  const strokeColor: string = getSelectedColor(id, edgeColor, theme);
  return (
    <marker {...buildMarkerAttributes(id, 13, 10, 12, 5)}>
      <path
        strokeWidth={1}
        fill={theme.palette.background.default}
        d={'m 5 1 L 12 5 L 5 9 L 5 1 z'}
        stroke={strokeColor}
      />
      <circle r={1} cx={1} cy={2} fill={strokeColor} stroke={'none'} />
      <circle r={1} cx={1} cy={8} fill={strokeColor} stroke={'none'} />
    </marker>
  );
};

const getSelectedColor = (id: string, edgeColor: string, theme: Theme): string => {
  const selectedColor: string =
    id.endsWith('--selected') && theme.palette.selected ? theme.palette.selected : getCSSColor(edgeColor, theme);
  return selectedColor;
};
