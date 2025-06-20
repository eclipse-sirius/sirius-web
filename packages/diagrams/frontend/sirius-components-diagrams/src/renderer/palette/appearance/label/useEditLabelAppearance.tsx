/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import {
  GQLEditLabelAppearanceData,
  GQLEditLabelAppearanceVariables,
  UseEditLabelAppearanceValue,
} from './useEditLabelAppearance.types';

export const editLabelAppearanceMutation = gql`
  mutation editLabelAppearance($input: EditLabelAppearanceInput!) {
    editLabelAppearance(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useEditLabelAppearance = (): UseEditLabelAppearanceValue => {
  const [editLabelAppearance, editLabelAppearanceResult] = useMutation<
    GQLEditLabelAppearanceData,
    GQLEditLabelAppearanceVariables
  >(editLabelAppearanceMutation);

  useReporting(editLabelAppearanceResult, (data: GQLEditLabelAppearanceData) => data.editLabelAppearance);

  const updateFontSize = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    fontSize: number
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            fontSize: fontSize,
          },
        },
      },
    });

  const updateItalic = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isItalic: boolean
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            italic: isItalic,
          },
        },
      },
    });

  const updateBold = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isBold: boolean
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            bold: isBold,
          },
        },
      },
    });

  const updateUnderline = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isUnderline: boolean
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            underline: isUnderline,
          },
        },
      },
    });

  const updateStrikeThrough = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isStrikeThrough: boolean
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            strikeThrough: isStrikeThrough,
          },
        },
      },
    });

  const updateBackground = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    background: string
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            background: background,
          },
        },
      },
    });

  const updateBorderColor = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderColor: string
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            borderColor: borderColor,
          },
        },
      },
    });

  const updateBorderRadius = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderRadius: number
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            borderRadius: borderRadius,
          },
        },
      },
    });

  const updateBorderSize = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderSize: number
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            borderSize: borderSize,
          },
        },
      },
    });

  const updateBorderStyle = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderStyle: string
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            borderStyle: borderStyle,
          },
        },
      },
    });

  const updateColor = (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    color: string
  ) =>
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId,
          labelId,
          appearance: {
            color: color,
          },
        },
      },
    });

  return {
    updateFontSize,
    updateItalic,
    updateBold,
    updateUnderline,
    updateStrikeThrough,
    updateBorderColor,
    updateBorderStyle,
    updateBorderSize,
    updateBorderRadius,
    updateColor,
    updateBackground,
  };
};
