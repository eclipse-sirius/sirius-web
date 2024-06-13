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
import { useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Slider from '@material-ui/core/Slider';
import gql from 'graphql-tag';
import { useEffect, useState } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLSlider } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditSliderInput,
  GQLEditSliderMutationData,
  GQLEditSliderMutationVariables,
  GQLEditSliderPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
} from './SliderPropertySection.types';

export const editSliderMutation = gql`
  mutation editSlider($input: EditSliderInput!) {
    editSlider(input: $input) {
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

const isErrorPayload = (payload: GQLEditSliderPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditSliderPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SliderPropertySection: PropertySectionComponent<GQLSlider> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLSlider>) => {
  const [editSlider, { loading, data, error }] = useMutation<GQLEditSliderMutationData, GQLEditSliderMutationVariables>(
    editSliderMutation
  );

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editSlider } = data;
        if (isErrorPayload(editSlider) || isSuccessPayload(editSlider)) {
          addMessages(editSlider.messages);
        }
      }
    }
  }, [loading, error, data]);

  const onValueChanged = (_, value) => {
    const input: GQLEditSliderInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      sliderId: widget.id,
      newValue: value,
    };
    const variables: GQLEditSliderMutationVariables = { input };
    editSlider({ variables });
  };

  const [displayedValue, setDisplayedValue] = useState<number>(widget.currentValue);
  useEffect(() => {
    setDisplayedValue(widget.currentValue);
  }, [widget.currentValue]);

  return (
    <div>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        data-testid={widget.label}
      />
      <Slider
        data-testid={widget.label}
        disabled={readOnly || widget.readOnly}
        min={widget.minValue}
        max={widget.maxValue}
        value={displayedValue}
        step={1}
        valueLabelDisplay="on"
        onChange={(_, value) => setDisplayedValue(value as number)}
        onChangeCommitted={onValueChanged}></Slider>
    </div>
  );
};
